package com.hu.fypimplbackend.utility

import com.hu.fypimplbackend.utility.mappers.CitySaveCityDTOMapper
import com.hu.fypimplbackend.utility.mappers.StateSaveStateDTOMapper
import com.hu.fypimplbackend.utility.mappers.UserUpdateUserMapper
import org.mapstruct.factory.Mappers

object MapperSingletons {
    val userUpdateUserMapper: UserUpdateUserMapper = Mappers.getMapper(UserUpdateUserMapper::class.java)
    val stateSaveStateDTOMapper: StateSaveStateDTOMapper = Mappers.getMapper(StateSaveStateDTOMapper::class.java)
    val citySaveCityDTOMapper: CitySaveCityDTOMapper = Mappers.getMapper(CitySaveCityDTOMapper::class.java)
}