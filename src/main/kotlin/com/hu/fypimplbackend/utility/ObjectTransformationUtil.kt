package com.hu.fypimplbackend.utility

import com.hu.fypimplbackend.domains.City
import com.hu.fypimplbackend.domains.State
import com.hu.fypimplbackend.dto.address.SaveCityDTO
import com.hu.fypimplbackend.dto.address.SaveStateDTO
import org.springframework.stereotype.Component

@Component
class ObjectTransformationUtil {
    fun getCityFromSaveCityDTO(cityDTO: SaveCityDTO): City {
        return City(cityName = cityDTO.cityName)
    }

    fun getStateFromSaveStateDTO(saveStateDTO: SaveStateDTO): State {
        return State(
            statName = saveStateDTO.statName,
            stateCode = saveStateDTO.stateCode,
            iso2 = saveStateDTO.iso2,
            activeStatus = saveStateDTO.activeStatus,
            latitude = saveStateDTO.latitude,
            longitude = saveStateDTO.longitude
        )
    }
}