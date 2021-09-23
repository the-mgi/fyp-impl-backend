package com.hu.fypimplbackend.dto.user

import com.hu.fypimplbackend.dto.address.AddressDTO

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