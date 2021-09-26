package com.hu.fypimplbackend.dto.address

data class SaveStateDTO(
    var statName: String,
    var stateCode: String,
    var countryId: Long,
    var iso2: String? = null,
    var activeStatus: Boolean = true,
    var latitude: Double,
    var longitude: Double
)