package com.hu.fypimplbackend.services

import com.hu.fypimplbackend.domains.Role
import com.hu.fypimplbackend.enums.RoleTypes

interface IRoleService {
    fun saveRole(role: Role): Role
    fun getAllRoles(): List<Role>
    fun deleteRole(roleId: Long)
    fun getRoleById(roleId: Long): Role
}