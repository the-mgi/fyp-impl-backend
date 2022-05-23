package com.hu.fypimplbackend.repositories

import com.hu.fypimplbackend.domains.Interest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InterestRepository : JpaRepository<Interest, Long> {
}