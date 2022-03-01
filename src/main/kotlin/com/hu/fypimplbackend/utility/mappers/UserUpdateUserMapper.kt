package com.hu.fypimplbackend.utility.mappers

import com.hu.fypimplbackend.domains.User
import com.hu.fypimplbackend.dto.UpdateUserDTO
import org.mapstruct.*

@Mapper(uses = [PresenceCheckUtils::class])
interface UserUpdateUserMapper {
    @BeanMapping(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    fun convertToModel(updateUserDTO: UpdateUserDTO, @MappingTarget user: User): User
}