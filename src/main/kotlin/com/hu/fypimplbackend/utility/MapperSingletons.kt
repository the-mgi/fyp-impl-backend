package com.hu.fypimplbackend.utility

import com.hu.fypimplbackend.utility.mappers.UserUpdateUserMapper
import org.mapstruct.factory.Mappers

object MapperSingletons {
    val userUpdateUserMapper: UserUpdateUserMapper = Mappers.getMapper(UserUpdateUserMapper::class.java)
}