package com.hu.fypimplbackend.services

import com.hu.fypimplbackend.domains.User
import com.hu.fypimplbackend.dto.user.ForgotPasswordDTO
import com.hu.fypimplbackend.dto.user.UpdateUserDTO
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.multipart.MultipartFile

interface IUserService : UserDetailsService {
    fun saveUser(user: User): User
    fun getUser(username: String): User
    fun deleteUser(username: String)
    fun updateProfileImage(username: String, multipartFile: MultipartFile): Pair<String, String>
    fun downloadImage(username: String): ByteArray
    fun updateUser(username: String, updateUserDTO: UpdateUserDTO): User
    fun forgotPassword(username: String): HashMap<String, String>
    fun updatePassword(username: String, forgotPasswordDTO: ForgotPasswordDTO): User
    fun getUserByUserId(userId: Long): User
    fun getBulkUserData(userIds: List<Long>): List<User>
}