package com.hu.fypimplbackend.utility.mappers

import com.hu.fypimplbackend.domains.City
import com.hu.fypimplbackend.dto.address.SaveCityDTO
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface CityMapper {
    fun dtoToEntity(saveCityDTO: SaveCityDTO): City
}