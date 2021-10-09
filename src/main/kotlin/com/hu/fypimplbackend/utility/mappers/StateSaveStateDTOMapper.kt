package com.hu.fypimplbackend.utility.mappers

import com.hu.fypimplbackend.domains.State
import com.hu.fypimplbackend.dto.address.SaveStateDTO
import org.mapstruct.*

@Mapper(uses = [PresenceCheckUtils::class])
interface StateSaveStateDTOMapper {
    @BeanMapping(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
    )
    fun convertToModel(saveStateDTO: SaveStateDTO): State
}