package com.hu.fypimplbackend.serviceImpls.address

import com.hu.fypimplbackend.domains.Country
import com.hu.fypimplbackend.repositories.address.CountryRepository
import com.hu.fypimplbackend.services.address.ICountryService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class CountryServiceImpl(
    @Autowired
    private val countryRepository: CountryRepository,

    @Autowired
    private val loggerFactory: Logger

) : ICountryService {
    @Throws(DataIntegrityViolationException::class)
    override fun saveCountry(country: Country): Country {
        loggerFactory.info("saveCountry in CountryServiceImpl")
        return if (country.countryId != null && this.countryRepository.existsById(country.countryId!!)) {
            throw DataIntegrityViolationException("Country with the same ID already exists")
        } else {
            this.countryRepository.save(country)
        }
    }

    override fun getAllCountries(): List<Country> {
        loggerFactory.info("getAllCountries in CountryServiceImpl")
        return this.countryRepository.findAll()
    }

    @Throws(EntityNotFoundException::class)
    override fun getCountryDetails(countryId: Long): Country {
        return this.countryRepository.getById(countryId)
    }
}