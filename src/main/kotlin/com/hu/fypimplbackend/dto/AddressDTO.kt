package com.hu.fypimplbackend.dto

data class AddressDTO(
    var cityId: Long,
    var addressLineOne: String,
    var addressLineTwo: String
)

data class SaveCityDTO(
    var cityName: String,
    var countryId: Long,
    var stateId: Long
)

data class SaveStateDTO(
    var statName: String,
    var stateCode: String,
    var countryId: Long,
    var iso2: String? = null,
    var activeStatus: Boolean = true,
    var latitude: Double,
    var longitude: Double
)