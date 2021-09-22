package com.hu.fypimplbackend.serviceImpls.address

import com.hu.fypimplbackend.domains.Country
import com.hu.fypimplbackend.repositories.address.CountryRepository
import com.hu.fypimplbackend.services.address.ICountryService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CountryServiceImpl(
    @Autowired
    private val countryRepository: CountryRepository,

    @Autowired
    private val loggerFactory: Logger

) : ICountryService {
    override fun saveCountry(country: Country): Country {
        loggerFactory.info("saveCountry in CountryServiceImpl")
        return this.countryRepository.save(country)
    }

    override fun getAllCountries(): List<Country> {
        loggerFactory.info("getAllCountries in CountryServiceImpl")
        return this.countryRepository.findAll()
    }
}