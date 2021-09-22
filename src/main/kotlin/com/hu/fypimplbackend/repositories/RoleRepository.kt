package com.hu.fypimplbackend.repositories

import com.hu.fypimplbackend.domains.Role
import com.hu.fypimplbackend.enums.RoleTypes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByRoleName(roleName: RoleTypes): Optional<Role>
}