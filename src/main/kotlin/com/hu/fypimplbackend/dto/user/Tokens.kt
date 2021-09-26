package com.hu.fypimplbackend.dto.user

data class Tokens(
    val accessToken: String,
    val refreshToken: String
)