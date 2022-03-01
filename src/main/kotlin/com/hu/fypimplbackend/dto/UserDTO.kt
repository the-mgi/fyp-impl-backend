package com.hu.fypimplbackend.dto

import com.hu.fypimplbackend.domains.Address

data class EmailPasswordDTO(
    var emailAddress: String? = null,
    var password: String? = null
)

data class ForgotPasswordDTO(
    val updatedPassword: String,
    val otpReceived: String
)

data class SignUpUserDTO(
    var firstName: String,
    var lastName: String,
    var password: String,
    var username: String,
    var emailAddress: String,
    var phoneNumber: String,
    var address: AddressDTO,
    var gender: String,
    var roles: List<Long>
)

data class Tokens(
    val accessToken: String,
    val refreshToken: String
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

data class UsernameAndPasswordDTO(
    val username: String? = null,
    val password: String? = null
)