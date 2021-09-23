package com.hu.fypimplbackend.serviceImpls.address

import com.hu.fypimplbackend.domains.State
import com.hu.fypimplbackend.repositories.address.StateRepository
import com.hu.fypimplbackend.services.address.IStateService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import kotlin.jvm.Throws

@Service
class StateServiceImpl(
    @Autowired
    private val stateRepository: StateRepository,

    @Autowired
    private val loggerFactory: Logger

) : IStateService {
    @Throws(DataIntegrityViolationException::class)
    override fun saveState(state: State): State {
        loggerFactory.info("saveState in StateServiceImpl")
        return this.stateRepository.save(state)
    }

    override fun getAllStates(): List<State> {
        loggerFactory.info("getAllStates in StateServiceImpl")
        return this.stateRepository.findAll()
    }

    @Throws(EntityNotFoundException::class)
    override fun getStateDetails(stateId: Long): State {
        return this.stateRepository.getById(stateId)
    }
}