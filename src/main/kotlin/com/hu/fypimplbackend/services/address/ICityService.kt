package com.hu.fypimplbackend.services.address

import com.hu.fypimplbackend.domains.City

interface ICityService {
    fun saveCity(city: City): City
    fun getAllCities(): List<City>
    fun getCityDetails(cityId: Long): City
}