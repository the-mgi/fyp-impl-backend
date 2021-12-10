package com.hu.fypimplbackend.serviceImpls

import com.hu.fypimplbackend.domains.User
import com.hu.fypimplbackend.dto.user.ForgotPasswordDTO
import com.hu.fypimplbackend.dto.user.UpdateUserDTO
import com.hu.fypimplbackend.exceptions.models.InvalidOTPCodeException
import com.hu.fypimplbackend.repositories.UserRepository
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
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.persistence.EntityNotFoundException

@Service
class UserServiceImpl(
    @Autowired
    private val userRepository: UserRepository,

    @Autowired
    private val passwordEncoder: BCryptPasswordEncoder,

    @Autowired
    private val emailSendService: EmailSendService,

    @Autowired
    private val loggerFactory: Logger

) : IUserService {
    private val userMapper = MapperSingletons.userUpdateUserMapper

    @Throws(DataIntegrityViolationException::class)
    override fun saveUser(user: User): User {
        this.loggerFactory.info("saveUser in UserService")
        user.password = this.passwordEncoder.encode(user.password)
        return userRepository.save(user).apply { password = null }
    }

    @Throws(EntityNotFoundException::class)
    override fun getUser(username: String): User {
        this.loggerFactory.info("getUser in UserService")
        return this.userRepository.getByUsername(username)
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

    override fun updatePassword(username: String, forgotPasswordDTO: ForgotPasswordDTO): User {
        val user = this.userRepository.getByUsername(username)
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
}