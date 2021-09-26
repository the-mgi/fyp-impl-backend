package com.hu.fypimplbackend.dto.address

data class SaveCityDTO(
    var cityName: String,
    var countryId: Long,
    var stateId: Long
)