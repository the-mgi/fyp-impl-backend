package com.hu.fypimplbackend.serviceImpls.address

import com.hu.fypimplbackend.domains.State
import com.hu.fypimplbackend.dto.SaveStateDTO
import com.hu.fypimplbackend.exceptions.NestedObjectDoesNotExistException
import com.hu.fypimplbackend.repositories.address.CountryRepository
import com.hu.fypimplbackend.repositories.address.StateRepository
import com.hu.fypimplbackend.services.address.IStateService
import com.hu.fypimplbackend.utility.mappers.StateSaveStateDTOMapper
import org.mapstruct.factory.Mappers
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class StateServiceImpl(
    @Autowired
    private val stateRepository: StateRepository,

    @Autowired
    private val countryRepository: CountryRepository,

    @Autowired
    private val loggerFactory: Logger

) : IStateService {
    private val stateSaveStateDTOMapper: StateSaveStateDTOMapper =
        Mappers.getMapper(StateSaveStateDTOMapper::class.java)

    @Throws(DataIntegrityViolationException::class, NestedObjectDoesNotExistException::class)
    override fun saveState(saveStateDTO: SaveStateDTO): State {
        loggerFactory.info("saveState in StateServiceImpl")
        val country = this.countryRepository.findById(saveStateDTO.countryId)
        return if (!country.isPresent) {
            throw NestedObjectDoesNotExistException("Country with ID ${saveStateDTO.countryId} does not exist")
        } else {
            val state = this.stateSaveStateDTOMapper.convertToModel(saveStateDTO)
            state.country = country.get()
            this.stateRepository.save(state)
        }
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