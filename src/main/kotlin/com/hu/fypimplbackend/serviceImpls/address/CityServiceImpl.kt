package com.hu.fypimplbackend.serviceImpls.address

import com.hu.fypimplbackend.domains.City
import com.hu.fypimplbackend.repositories.address.CityRepository
import com.hu.fypimplbackend.services.address.ICityService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CityServiceImpl(
    @Autowired
    private val cityRepository: CityRepository,

    @Autowired
    private val loggerFactory: Logger

) : ICityService {
    override fun saveCity(city: City): City {
        loggerFactory.info("saveCity in CityServiceImpl")
        return this.cityRepository.save(city)
    }

    override fun getAllCities(): List<City> {
        loggerFactory.info("getAllCities in CityServiceImpl")
        return this.cityRepository.findAll()
    }
}