package com.hu.fypimplbackend.services.address

import com.hu.fypimplbackend.domains.City
import com.hu.fypimplbackend.dto.SaveCityDTO

interface ICityService {
    fun saveCity(saveCityDTO: SaveCityDTO): City
    fun getAllCities(): List<City>
    fun getCityDetails(cityId: Long): City
}