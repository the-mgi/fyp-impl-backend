package com.hu.fypimplbackend.services

import com.hu.fypimplbackend.domains.Interest

interface InterestService {
    fun getAllInterest(): List<Interest>
}