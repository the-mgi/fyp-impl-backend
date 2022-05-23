package com.hu.fypimplbackend.controllers

import com.hu.fypimplbackend.dto.response.BaseResponse
import com.hu.fypimplbackend.dto.response.SuccessResponseDTO.Companion.getSuccessObject
import com.hu.fypimplbackend.services.InterestService
import com.hu.fypimplbackend.utility.INTEREST_ROUTE
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(INTEREST_ROUTE)
class InterestController(
    @Autowired
    private val interestService: InterestService,

    @Autowired
    private val loggerFactory: Logger
) {
    @GetMapping("/get-all-interest")
    fun getAllInterest(): ResponseEntity<BaseResponse> {
        this.loggerFactory.info("getAllInterest in InterestController")
        return getSuccessObject(this.interestService.getAllInterest(), HttpStatus.OK.value())
    }
}