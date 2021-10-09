package com.hu.fypimplbackend.utility.mappers

import com.hu.fypimplbackend.domains.City
import com.hu.fypimplbackend.dto.address.SaveCityDTO
import org.mapstruct.*

@Mapper(uses = [PresenceCheckUtils::class])
interface CitySaveCityDTOMapper {
    @BeanMapping(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    fun convertToModel(saveCityDTO: SaveCityDTO): City
}