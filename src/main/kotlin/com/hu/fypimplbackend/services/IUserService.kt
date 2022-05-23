package com.hu.fypimplbackend.services

import com.hu.fypimplbackend.domains.Friends
import com.hu.fypimplbackend.domains.User
import com.hu.fypimplbackend.dto.*
import com.hu.fypimplbackend.enums.RequestStatusType
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

interface IUserService : UserDetailsService {
    fun saveUser(user: User): User
    fun getUser(httpServletRequest: HttpServletRequest): User
    fun deleteUser(username: String)
    fun updateProfileImage(username: String, multipartFile: MultipartFile): Pair<String, String>
    fun downloadImage(username: String): ByteArray
    fun updateUser(username: String, updateUserDTO: UpdateUserDTO): User
    fun forgotPassword(username: String): HashMap<String, String>
    fun updatePassword(forgotPasswordDTO: ForgotPasswordDTO): User
    fun getUserByUserId(userId: Long): User
    fun getBulkUserData(userIds: List<Long>): List<User>
    fun searchUserByAnyParameter(searchUserDTO: SearchUserDTO): List<User>
    fun sendFriendRequest(friendRequestDTO: FriendRequestDTO, request: HttpServletRequest)
    fun acceptOrDenyFriendRequest(friendRequestDTO: FriendRequestDTO, request: HttpServletRequest, status: RequestStatusType)
    fun getAllFriends(username: String, request: HttpServletRequest): List<User>
    fun getAllSentRequest(username: String, request: HttpServletRequest): List<User>
    fun getUserDetails(username: String): User
    fun getRequestStatus(username: String, request: HttpServletRequest): Friends
    fun uploadProfileImage(uploadImageDTO: UploadImageDTO): Any
}