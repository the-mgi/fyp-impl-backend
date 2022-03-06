package com.hu.fypimplbackend.dto

import com.hu.fypimplbackend.domains.Address

data class EmailPasswordDTO(
    var emailAddress: String? = null,
    var password: String? = null
)

data class ForgotPasswordDTO(
    val username: String,
    val updatedPassword: String,
    val otpReceived: String
)

data class TokensDTO(
    val accessToken: String,
    val refreshToken: String
)

data class UpdateUserDTO(
    val firstName: String? = null,
    val lastName: String? = null,
    val password: String? = null,
    val phoneNumber: String? = null,
    val address: Address? = null,
    val imagePath: String? = null,
    val imageFileName: String? = null
)

data class UsernameDTO(
    val username: String? = null
)