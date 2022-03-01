package com.hu.fypimplbackend.utility.mappers

import com.hu.fypimplbackend.domains.State
import com.hu.fypimplbackend.dto.SaveStateDTO
import org.mapstruct.BeanMapping
import org.mapstruct.Mapper
import org.mapstruct.NullValueCheckStrategy
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(uses = [PresenceCheckUtils::class])
interface StateSaveStateDTOMapper {
    @BeanMapping(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    fun convertToModel(saveStateDTO: SaveStateDTO): State
}