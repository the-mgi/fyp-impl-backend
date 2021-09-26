package com.hu.fypimplbackend.utility

import com.hu.fypimplbackend.domains.City
import com.hu.fypimplbackend.dto.address.SaveCityDTO
import org.springframework.stereotype.Component

@Component
class ObjectTransformationUtil {
    fun getCityFromCityDTO(cityDTO: SaveCityDTO): City {
        return City(cityName = cityDTO.cityName)
    }
}