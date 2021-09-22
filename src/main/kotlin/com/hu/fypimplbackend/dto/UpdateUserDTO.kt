package com.kdsp.ds.dto

import com.kdsp.ds.domains.Address

data class UpdateUserDTO(
    var firstName: String? = null,
    var lastName: String? = null,
    var password: String? = null,
    var phoneNumber: String? = null,
    var address: Address? = null,
    var imagePath: String? = null,
    var imageFileName: String? = null
)