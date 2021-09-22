package com.hu.fypimplbackend.repositories

import com.hu.fypimplbackend.domains.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, Long>