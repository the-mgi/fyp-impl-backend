package com.hu.fypimplbackend.serviceImpls

import com.hu.fypimplbackend.domains.Friends
import com.hu.fypimplbackend.domains.Role
import com.hu.fypimplbackend.domains.User
import com.hu.fypimplbackend.dto.*
import com.hu.fypimplbackend.enums.RequestStatusType
import com.hu.fypimplbackend.enums.RoleTypes
import com.hu.fypimplbackend.exceptions.InvalidOTPCodeException
import com.hu.fypimplbackend.exceptions.ResourceAccessForbidden
import com.hu.fypimplbackend.repositories.FriendsRepository
import com.hu.fypimplbackend.repositories.UserRepository
import com.hu.fypimplbackend.security.JWTDecodedData
import com.hu.fypimplbackend.security.JwtUtil
import com.hu.fypimplbackend.services.IUserService
import com.hu.fypimplbackend.utility.EmailSendService
import com.hu.fypimplbackend.utility.MapperSingletons
import com.hu.fypimplbackend.utility.generateString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import javax.persistence.EntityNotFoundException
import javax.servlet.http.HttpServletRequest

@Service
class UserServiceImpl(
    @Autowired
    private val userRepository: UserRepository,

    @Autowired
    private val passwordEncoder: BCryptPasswordEncoder,

    @Autowired
    private val emailSendService: EmailSendService,

    @Autowired
    private val jwtUtil: JwtUtil,

    @Autowired
    private val friendsRepository: FriendsRepository,

    @Autowired
    private val restTemplate: RestTemplate,

    @Autowired
    private val loggerFactory: Logger

) : IUserService {
    private val userMapper = MapperSingletons.userUpdateUserMapper

    @Throws(DataIntegrityViolationException::class)
    override fun saveUser(user: User): User {
        this.loggerFactory.info("saveUser in UserService")
        user.password = this.passwordEncoder.encode(user.password)
        user.roles.add(Role(id = 1, roleName = RoleTypes.PLAYER))
        return userRepository.save(user).apply { password = null }
    }

    @Throws(EntityNotFoundException::class)
    override fun getUser(httpServletRequest: HttpServletRequest): User {
        this.loggerFactory.info("getUser in UserService")
        val jwtDecodedData: JWTDecodedData = this.jwtUtil.getDecodedToken(httpServletRequest)!!
        return this.userRepository.getByUsername(jwtDecodedData.subject).apply { password = null }
    }

    override fun deleteUser(username: String) {
        this.userRepository.deleteByUsername(username)
    }

    override fun updateProfileImage(username: String, multipartFile: MultipartFile): Pair<String, String> {
        TODO("Not yet implemented")
    }

    @Throws(EntityNotFoundException::class)
    override fun downloadImage(username: String): ByteArray {
        TODO("Not yet implemented")
    }

    @Throws(UsernameNotFoundException::class, EntityNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = this.userRepository.getByUsername(username)
        val authorities: MutableList<SimpleGrantedAuthority> = ArrayList()
        user.roles.forEach { authorities.add(SimpleGrantedAuthority(it.roleName?.name)) }
        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            authorities
        )
    }

    @Throws(EntityNotFoundException::class)
    override fun updateUser(username: String, updateUserDTO: UpdateUserDTO): User {
        val oldUserOptional = this.userRepository.getByUsername(username)
        return this.userRepository.save(
            userMapper.convertToModel(updateUserDTO, oldUserOptional)
                .apply { password = passwordEncoder.encode(password) })
            .apply { password = null }
    }

    @Throws(EntityNotFoundException::class)
    override fun forgotPassword(username: String): HashMap<String, String> {
        val user = this.userRepository.getByUsername(username)
        val otpCode = generateString()
        user.otpCode = otpCode
        GlobalScope.launch(Dispatchers.IO) {
            val job = async { emailSendService.sendEmail(user.emailAddress!!, otpCode) }
            job.await()
            userRepository.save(user)
        }
        return hashMapOf(
            "message" to "If your account exists, you've received an OTP code"
        )
    }

    override fun updatePassword(forgotPasswordDTO: ForgotPasswordDTO): User {
        val user = this.userRepository.getByUsername(forgotPasswordDTO.username)
        if (user.otpCode != forgotPasswordDTO.otpReceived) {
            throw InvalidOTPCodeException()
        }
        user.otpCode = null
        user.password = this.passwordEncoder.encode(forgotPasswordDTO.updatedPassword)
        return this.userRepository.save(user).apply { password = null }
    }

    @Throws(EntityNotFoundException::class)
    override fun getUserByUserId(userId: Long): User {
        this.loggerFactory.info("getUser in UserService")
        return this.userRepository.getById(userId).apply { password = null }
    }

    override fun getBulkUserData(userIds: List<Long>): List<User> {
        this.loggerFactory.info("getBulkUserData in UserService")
        return this.userRepository.findAllById(userIds)
    }

    override fun searchUserByAnyParameter(searchUserDTO: SearchUserDTO): List<User> {
        this.loggerFactory.info("searchUserByAnyParameter in UserServiceImpl")
        val listOfUsers = this.userRepository.searchUsersByAllParams(searchUserDTO.searchUserString);
        listOfUsers.forEach { it.password = null; it.roles = mutableListOf() }
        return listOfUsers
    }

    @Throws(EntityNotFoundException::class)
    override fun sendFriendRequest(friendRequestDTO: FriendRequestDTO, request: HttpServletRequest) {
        this.loggerFactory.info("sendFriendRequest in UserServiceImpl")
        val jwtDecodedData = this.jwtUtil.getDecodedToken(request)
        if (jwtDecodedData != null) {
            if (jwtDecodedData.subject != friendRequestDTO.fromUsername) {
                throw ResourceAccessForbidden("Cannot send request on behalf of another user")
            }
        } else {
            throw ResourceAccessForbidden("Authorization Header must be present!")
        }
        val optionalFriend =
            this.friendsRepository.findByFromUsernameAndToUsername(
                friendRequestDTO.fromUsername,
                friendRequestDTO.toUsername
            )
        if (!optionalFriend.isPresent) {
            this.friendsRepository.save(
                Friends(
                    friendShipStatus = RequestStatusType.FRIEND_REQUEST_SENT,
                    fromUsername = friendRequestDTO.fromUsername,
                    toUsername = friendRequestDTO.toUsername
                )
            )
            return
        }

        throw RuntimeException("Cannot send request to ${friendRequestDTO.toUsername}. Unexpected error Occurred.")
    }

    @Throws(EntityNotFoundException::class)
    override fun acceptOrDenyFriendRequest(
        friendRequestDTO: FriendRequestDTO,
        request: HttpServletRequest,
        status: RequestStatusType
    ) {
        this.loggerFactory.info("deleteSentRequest in UserServiceImpl")
        val jwtDecodedData = this.jwtUtil.getDecodedToken(request)
        if (jwtDecodedData != null) {
            if (jwtDecodedData.subject != friendRequestDTO.fromUsername) {
                throw ResourceAccessForbidden("Cannot send request on behalf of another user")
            }
        } else {
            throw ResourceAccessForbidden("Authorization Token must be valid")
        }
        val optionalFriend = this.friendsRepository.findByFromUsernameAndToUsername(
            friendRequestDTO.toUsername,
            friendRequestDTO.fromUsername
        )
        if (optionalFriend.isPresent) {
            this.friendsRepository.save(
                optionalFriend.get().apply { friendShipStatus = RequestStatusType.FRIEND_REQUEST_ACCEPTED })
            return
        }
        throw RuntimeException("Cannot accept request")
    }

    @Throws(EntityNotFoundException::class)
    override fun getAllFriends(username: String, request: HttpServletRequest): List<User> {
        this.loggerFactory.info("getAllFriends in UserServiceImpl")
        val jwtDecodedData = this.jwtUtil.getDecodedToken(request)
        if (jwtDecodedData != null) {
            if (jwtDecodedData.subject != username) {
                throw ResourceAccessForbidden("Un-Authorized")
            }
        } else {
            throw ResourceAccessForbidden("Authorization Token must be valid")
        }
        val mutableList = this.friendsRepository.findAllByFromUsernameAndFriendShipStatus(
            username,
            RequestStatusType.FRIEND_REQUEST_ACCEPTED
        ).map { this.userRepository.getByUsername(it.toUsername!!) } as MutableList
        val secondSide = this.friendsRepository.findAllByToUsernameAndFriendShipStatus(
            username,
            RequestStatusType.FRIEND_REQUEST_ACCEPTED
        ).map { this.userRepository.getByUsername(it.fromUsername!!) }
        mutableList.addAll(secondSide)
        return mutableList
    }

    @Throws(EntityNotFoundException::class)
    override fun getAllSentRequest(username: String, request: HttpServletRequest): List<User> {
        this.loggerFactory.info("getAllSentRequest in UserServiceImpl")
        val jwtDecodedData = this.jwtUtil.getDecodedToken(request)
        if (jwtDecodedData != null) {
            if (jwtDecodedData.subject != username) {
                throw ResourceAccessForbidden("Un-Authorized")
            }
        } else {
            throw ResourceAccessForbidden("Authorization Token must be valid")
        }

        TODO()
    }

    override fun getUserDetails(username: String): User {
        return this.userRepository.getByUsername(username).apply { password = null }
    }

    @Throws(EntityNotFoundException::class)
    override fun getRequestStatus(username: String, request: HttpServletRequest): Friends {
        this.loggerFactory.info("getRequestStatus in UserServiceImpl")
        val jwtDecodedData = this.jwtUtil.getDecodedToken(request)
        if (jwtDecodedData != null) {
            val friend = this.friendsRepository.findByFromUsernameAndToUsername(jwtDecodedData.subject, username)
            return if (!friend.isPresent) {
                this.friendsRepository.findByFromUsernameAndToUsername(username, jwtDecodedData.subject).get()
            } else {
                friend.get()
            }
        } else {
            throw ResourceAccessForbidden("Authorization Token must be valid")
        }
    }

    @Throws(EntityNotFoundException::class)
    override fun uploadProfileImage(uploadImageDTO: UploadImageDTO): Any {
        this.loggerFactory.info("getAllFriends in UserServiceImpl")
        val user = this.userRepository.getByUsername(uploadImageDTO.username)
        user.imagePath = uploadImageDTO.imageUrl
        user.imageFileName = uploadImageDTO.imagePathName
        return this.userRepository.save(user).apply { password = null }
    }
}