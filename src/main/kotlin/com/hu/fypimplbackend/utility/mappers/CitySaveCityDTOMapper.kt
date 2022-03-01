package com.hu.fypimplbackend.utility.mappers

import com.hu.fypimplbackend.domains.City
import com.hu.fypimplbackend.dto.SaveCityDTO
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(uses = [PresenceCheckUtils::class])
interface CitySaveCityDTOMapper {
    @BeanMapping(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    fun convertToModel(saveCityDTO: SaveCityDTO): City
}