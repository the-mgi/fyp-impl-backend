package com.hu.fypimplbackend.serviceImpls

import com.hu.fypimplbackend.domains.Role
import com.hu.fypimplbackend.repositories.RoleRepository
import com.hu.fypimplbackend.services.IRoleService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class RoleServiceImpl(
    @Autowired
    private val roleRepository: RoleRepository,

    @Autowired
    private val loggerFactory: Logger

) : IRoleService {

    @Throws(DataIntegrityViolationException::class)
    override fun saveRole(role: Role): Role {
        this.loggerFactory.info("saveRole in RoleServiceImpl")
        return if (role.id != null && this.roleRepository.existsById(role.id!!)) {
            throw DataIntegrityViolationException("Role with the same ID already exists")
        } else {
            this.roleRepository.save(role)
        }
    }

    override fun getAllRoles(): List<Role> {
        this.loggerFactory.info("getAllRoles in RoleServiceImpl")
        return this.roleRepository.findAll()
    }

    override fun deleteRole(roleId: Long) {
        this.roleRepository.deleteById(roleId)
    }

    @Throws(EntityNotFoundException::class)
    override fun getRoleById(roleId: Long): Role {
        return this.roleRepository.getById(roleId)
    }
}