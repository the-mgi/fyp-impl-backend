package com.hu.fypimplbackend.controllers

import com.hu.fypimplbackend.domains.Role
import com.hu.fypimplbackend.dto.response.BaseResponse
import com.hu.fypimplbackend.dto.response.SuccessResponseDTO.Companion.getDeleteResponse
import com.hu.fypimplbackend.dto.response.SuccessResponseDTO.Companion.getSuccessObject
import com.hu.fypimplbackend.enums.RoleTypes
import com.hu.fypimplbackend.services.IRoleService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/role")
class RoleController(
    @Autowired
    private val iRoleService: IRoleService,

    private val loggerFactory: Logger
) {

    @PostMapping("/save")
    fun saveRole(@RequestBody role: Role): ResponseEntity<BaseResponse> {
        this.loggerFactory.info("saveRole in RoleController")
        return getSuccessObject(this.iRoleService.saveRole(role), HttpStatus.CREATED.value())

    }

    @DeleteMapping("/delete/{roleId}")
    fun deleteRole(@PathVariable("roleId") roleId: Long): ResponseEntity<Any> {
        this.loggerFactory.info("deleteRole in RoleController")
        this.iRoleService.deleteRole(roleId)
        return getDeleteResponse()
    }

    @GetMapping("/{roleId}")
    fun getRole(@PathVariable("roleId") roleId: Long): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iRoleService.getRoleById(roleId), HttpStatus.OK.value())
    }

    @GetMapping("/all")
    fun getAllRoles(): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iRoleService.getAllRoles(), HttpStatus.OK.value())
    }
}