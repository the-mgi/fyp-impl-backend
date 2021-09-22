package com.hu.fypimplbackend.repositories

import com.hu.fypimplbackend.domains.City
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CityRepository : JpaRepository<City, Long>