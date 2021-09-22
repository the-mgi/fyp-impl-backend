package com.hu.fypimplbackend.repositories.address

import com.hu.fypimplbackend.domains.State
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StateRepository : JpaRepository<State, Long>