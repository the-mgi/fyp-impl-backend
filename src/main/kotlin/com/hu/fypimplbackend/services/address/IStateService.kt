package com.hu.fypimplbackend.services.address

import com.hu.fypimplbackend.domains.State

interface IStateService {
    fun saveState(state: State): State
    fun getAllStates(): List<State>
}