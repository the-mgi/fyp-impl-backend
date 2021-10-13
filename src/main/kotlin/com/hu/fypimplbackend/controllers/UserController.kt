package com.hu.fypimplbackend.controllers

import com.hu.fypimplbackend.domains.User
import com.hu.fypimplbackend.dto.response.BaseResponse
import com.hu.fypimplbackend.dto.response.SuccessResponseDTO.Companion.getDeleteResponse
import com.hu.fypimplbackend.dto.response.SuccessResponseDTO.Companion.getSuccessObject
import com.hu.fypimplbackend.dto.user.ForgotPasswordDTO
import com.hu.fypimplbackend.dto.user.UpdateUserDTO
import com.hu.fypimplbackend.services.IUserService
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    @Autowired
    private val iUserService: IUserService,

    @Autowired
    private val loggerFactory: Logger

) {
    @PostMapping("/save")
    fun saveUser(@RequestBody user: User): ResponseEntity<BaseResponse> {
        loggerFactory.info("saveUser in UserController")
        return getSuccessObject(this.iUserService.saveUser(user), HttpStatus.CREATED.value())
    }

    @PutMapping("/edit/{username}")
    fun updateUser(
        @RequestBody updateUserDTO: UpdateUserDTO,
        @PathVariable("username") username: String
    ): ResponseEntity<BaseResponse> {
        loggerFactory.info("updateUser in UserController")
        return getSuccessObject(this.iUserService.updateUser(username, updateUserDTO), HttpStatus.OK.value())
    }

    @DeleteMapping("/delete/{username}")
    fun deleteUser(@PathVariable("username") username: String): ResponseEntity<Any> {
        loggerFactory.info("deleteUser in UserController")
        this.iUserService.deleteUser(username)
        return getDeleteResponse()
    }

    @GetMapping("/{username}")
    fun getUser(@PathVariable("username") username: String): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iUserService.getUser(username), HttpStatus.OK.value())
    }

    @GetMapping("/userId/{userId}")
    fun getUserByUserId(@PathVariable("userId") userId: Long): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iUserService.getUserByUserId(userId), HttpStatus.OK.value())
    }

    @PatchMapping("/forgot-password/{username}")
    fun forgotPassword(@PathVariable("username") username: String): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iUserService.forgotPassword(username), HttpStatus.OK.value())
    }

    @PatchMapping("/update-password/{username}")
    fun updatePassword(
        @PathVariable("username") username: String,
        @RequestBody forgotPasswordDTO: ForgotPasswordDTO
    ): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iUserService.updatePassword(username, forgotPasswordDTO), HttpStatus.OK.value())
    }
}