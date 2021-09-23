package com.hu.fypimplbackend.serviceImpls.address

import com.hu.fypimplbackend.domains.State
import com.hu.fypimplbackend.repositories.address.StateRepository
import com.hu.fypimplbackend.services.address.IStateService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StateServiceImpl(
    @Autowired
    private val stateRepository: StateRepository,

    @Autowired
    private val loggerFactory: Logger

) : IStateService {
    override fun saveState(state: State): State {
        loggerFactory.info("saveState in StateServiceImpl")
        return this.stateRepository.save(state)
    }

    override fun getAllStates(): List<State> {
        loggerFactory.info("getAllStates in StateServiceImpl")
        return this.stateRepository.findAll()
    }
}