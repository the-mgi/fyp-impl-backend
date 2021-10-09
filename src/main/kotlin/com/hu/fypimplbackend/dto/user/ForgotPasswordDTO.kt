package com.hu.fypimplbackend.dto.user

data class ForgotPasswordDTO(
    val updatedPassword: String,
    val otpReceived: String
)