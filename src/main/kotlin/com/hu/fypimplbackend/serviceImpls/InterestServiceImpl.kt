package com.hu.fypimplbackend.serviceImpls

import com.hu.fypimplbackend.domains.Interest
import com.hu.fypimplbackend.repositories.InterestRepository
import com.hu.fypimplbackend.services.InterestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InterestServiceImpl(
    @Autowired
    private val interestRepository: InterestRepository
) : InterestService {
    override fun getAllInterest(): List<Interest> {
        return this.interestRepository.findAll()
    }
}