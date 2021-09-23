package com.hu.fypimplbackend.services.address

import com.hu.fypimplbackend.domains.Country

interface ICountryService {
    fun saveCountry(country: Country): Country
    fun getAllCountries(): List<Country>
}