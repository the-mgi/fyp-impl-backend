package com.hu.fypimplbackend.serviceImpls.address

import com.hu.fypimplbackend.domains.City
import com.hu.fypimplbackend.dto.address.SaveCityDTO
import com.hu.fypimplbackend.exceptions.models.NestedObjectDoesNotExistException
import com.hu.fypimplbackend.repositories.address.CityRepository
import com.hu.fypimplbackend.repositories.address.CountryRepository
import com.hu.fypimplbackend.repositories.address.StateRepository
import com.hu.fypimplbackend.services.address.ICityService
import com.hu.fypimplbackend.utility.ObjectTransformationUtil
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class CityServiceImpl(
    @Autowired
    private val cityRepository: CityRepository,

    @Autowired
    private val stateRepository: StateRepository,

    @Autowired
    private val countryRepository: CountryRepository,

    @Autowired
    private val objectTransformationUtil: ObjectTransformationUtil,

    @Autowired
    private val loggerFactory: Logger,

    ) : ICityService {

    @Throws(DataIntegrityViolationException::class, NestedObjectDoesNotExistException::class)
    override fun saveCity(saveCityDTO: SaveCityDTO): City {
        loggerFactory.info("saveCity in CityServiceImpl")
        val country = this.countryRepository.findById(saveCityDTO.countryId)
        val state = this.stateRepository.findById(saveCityDTO.stateId)
        return if (!country.isPresent) {
            throw NestedObjectDoesNotExistException("Country with ID ${saveCityDTO.countryId} does not exist")
        } else if (!state.isPresent) {
            throw NestedObjectDoesNotExistException("State with ID ${saveCityDTO.stateId} does not exist")
        } else {
            val city = this.objectTransformationUtil.getCityFromSaveCityDTO(saveCityDTO)
            city.country = country.get()
            city.state = state.get()
            this.cityRepository.save(city)
        }
    }

    override fun getAllCities(): List<City> {
        loggerFactory.info("getAllCities in CityServiceImpl")
        return this.cityRepository.findAll()
    }

    @Throws(EntityNotFoundException::class)
    override fun getCityDetails(cityId: Long): City {
        return this.cityRepository.getById(cityId)
    }
}