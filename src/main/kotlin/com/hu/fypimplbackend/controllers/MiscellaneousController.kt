package com.hu.fypimplbackend.controllers

import com.hu.fypimplbackend.domains.Country
import com.hu.fypimplbackend.dto.address.SaveCityDTO
import com.hu.fypimplbackend.dto.address.SaveStateDTO
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
import javax.validation.Valid

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
    fun saveCountry(@Valid @RequestBody country: Country): ResponseEntity<BaseResponse> {
        loggerFactory.info("saveCountry in MiscellaneousController")
        return getSuccessObject(this.iCountryService.saveCountry(country), HttpStatus.CREATED.value())
    }

    @GetMapping("/country/all")
    fun getAllCountries(): ResponseEntity<BaseResponse> {
        loggerFactory.info("getAllCountries in MiscellaneousController")
        return getSuccessObject(this.iCountryService.getAllCountries(), HttpStatus.OK.value())
    }

    @GetMapping("/country/{countryId}")
    fun getCountryDetails(@PathVariable("countryId") countryId: Long): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iCountryService.getCountryDetails(countryId), HttpStatus.OK.value())
    }

    @PostMapping("/state/save")
    fun saveState(@Valid @RequestBody saveStateDTO: SaveStateDTO): ResponseEntity<BaseResponse> {
        loggerFactory.info("saveState in MiscellaneousController")
        return getSuccessObject(this.iStateService.saveState(saveStateDTO), HttpStatus.CREATED.value())
    }

    @GetMapping("/state/all")
    fun getAllStates(): ResponseEntity<BaseResponse> {
        loggerFactory.info("getAllStates in MiscellaneousController")
        return getSuccessObject(this.iStateService.getAllStates(), HttpStatus.OK.value())
    }

    @GetMapping("/state/{stateId}")
    fun getStateDetails(@PathVariable("stateId") stateId: Long): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iStateService.getStateDetails(stateId), HttpStatus.OK.value())
    }

    @PostMapping("/city/save")
    fun saveCity(@Valid @RequestBody saveCityDTO: SaveCityDTO): ResponseEntity<BaseResponse> {
        loggerFactory.info("saveCity in MiscellaneousController")
        return getSuccessObject(this.iCityService.saveCity(saveCityDTO), HttpStatus.CREATED.value())
    }

    @GetMapping("/city/all")
    fun getAllCities(): ResponseEntity<BaseResponse> {
        loggerFactory.info("getAllCities in MiscellaneousController")
        return getSuccessObject(this.iCityService.getAllCities(), HttpStatus.OK.value())
    }

    @GetMapping("/city/{cityId}")
    fun getCityDetails(@PathVariable("cityId") cityId: Long): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iCityService.getCityDetails(cityId), HttpStatus.OK.value())
    }
}