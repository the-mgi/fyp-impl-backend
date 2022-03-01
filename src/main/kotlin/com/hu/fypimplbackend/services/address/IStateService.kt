package com.hu.fypimplbackend.services.address

import com.hu.fypimplbackend.domains.State
import com.hu.fypimplbackend.dto.SaveStateDTO

interface IStateService {
    fun saveState(saveStateDTO: SaveStateDTO): State
    fun getAllStates(): List<State>
    fun getStateDetails(stateId: Long): State
}