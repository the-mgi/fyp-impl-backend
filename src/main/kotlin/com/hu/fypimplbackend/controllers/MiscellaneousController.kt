package com.hu.fypimplbackend.controllers

import com.hu.fypimplbackend.domains.City
import com.hu.fypimplbackend.domains.Country
import com.hu.fypimplbackend.domains.State
import com.hu.fypimplbackend.dto.response.BaseResponse
import com.hu.fypimplbackend.dto.response.SuccessResponseDTO.Companion.getSuccessObject
import com.hu.fypimplbackend.services.address.ICityService
import com.hu.fypimplbackend.services.address.ICountryService
import com.hu.fypimplbackend.services.address.IStateService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/miscellaneous")
class MiscellaneousController(
    @Autowired
    private val iCountryService: ICountryService,

    @Autowired
    private val iStateService: IStateService,

    @Autowired
    private val iCityService: ICityService,

    @Autowired
    private val loggerFactory: Logger

) {
    @PostMapping("/country/save")
    fun saveCountry(@RequestBody country: Country): ResponseEntity<BaseResponse> {
        loggerFactory.info("saveCountry in MiscellaneousController")
        return getSuccessObject(this.iCountryService.saveCountry(country), HttpStatus.CREATED)
    }

    @GetMapping("/country/all")
    fun getAllCountries(): ResponseEntity<BaseResponse> {
        loggerFactory.info("getAllCountries in MiscellaneousController")
        return getSuccessObject(this.iCountryService.getAllCountries(), HttpStatus.OK)
    }

    @PostMapping("/state/save")
    fun saveState(@RequestBody state: State) : ResponseEntity<BaseResponse> {
        loggerFactory.info("saveState in MiscellaneousController")
        return getSuccessObject(this.iStateService.saveState(state), HttpStatus.CREATED)
    }

    @GetMapping("/state/all")
    fun getAllStates() : ResponseEntity<BaseResponse> {
        loggerFactory.info("getAllStates in MiscellaneousController")
        return getSuccessObject(this.iStateService.getAllStates(), HttpStatus.OK)
    }

    @PostMapping("/city/save")
    fun saveCity(@RequestBody city: City) : ResponseEntity<BaseResponse> {
        loggerFactory.info("saveCity in MiscellaneousController")
        return getSuccessObject(this.iCityService.saveCity(city), HttpStatus.CREATED)
    }

    @GetMapping("/city/all")
    fun getAllCities(): ResponseEntity<BaseResponse> {
        loggerFactory.info("getAllCities in MiscellaneousController")
        return getSuccessObject(this.iCityService.getAllCities(), HttpStatus.OK)
    }
}