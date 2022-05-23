package com.hu.fypimplbackend.repositories

import com.hu.fypimplbackend.domains.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun getByUsername(username: String): User
    fun deleteByUsername(username: String)

    @Query("SELECT u FROM User u WHERE u.emailAddress LIKE %:searchString% OR u.firstName LIKE %:searchString% OR u.lastName LIKE %:searchString% OR concat(u.firstName, '', u.lastName) LIKE %:searchString% OR u.username LIKE %:searchString%")
    fun searchUsersByAllParams(@Param("searchString") searchString: String): List<User>
}