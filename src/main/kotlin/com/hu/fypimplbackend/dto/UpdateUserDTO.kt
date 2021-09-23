package com.hu.fypimplbackend.dto

import com.hu.fypimplbackend.domains.Address

data class UpdateUserDTO(
    val firstName: String? = null,
    val lastName: String? = null,
    val password: String? = null,
    val phoneNumber: String? = null,
    val address: Address? = null,
    val imagePath: String? = null,
    val imageFileName: String? = null
)