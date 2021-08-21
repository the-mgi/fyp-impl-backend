package com.hu.fypimplbackend.services

import org.springframework.web.multipart.MultipartFile

interface UserService {
    fun updateProfileImage(username: String, multipartFile: MultipartFile): Pair<String, String>
    fun downloadImage(username: String): ByteArray
}