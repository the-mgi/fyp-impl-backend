package com.hu.fypimplbackend.repositories

import com.hu.fypimplbackend.domains.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): Optional<User>
    fun deleteByUsername(username: String)
}