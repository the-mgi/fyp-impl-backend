package com.hu.fypimplbackend.controllers

import com.hu.fypimplbackend.domains.User
import com.hu.fypimplbackend.dto.*
import com.hu.fypimplbackend.dto.response.BaseResponse
import com.hu.fypimplbackend.dto.response.SuccessResponseDTO
import com.hu.fypimplbackend.dto.response.SuccessResponseDTO.Companion.getDeleteResponse
import com.hu.fypimplbackend.dto.response.SuccessResponseDTO.Companion.getSuccessObject
import com.hu.fypimplbackend.dto.response.SuccessResponseDTO.Companion.getSuccessResponse
import com.hu.fypimplbackend.enums.RequestStatusType
import com.hu.fypimplbackend.services.IUserService
import com.hu.fypimplbackend.utility.USER_ROUTE
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(USER_ROUTE)
class UserController(
    @Autowired
    private val iUserService: IUserService,

    @Autowired
    private val restTemplate: RestTemplate,

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

    @GetMapping
    fun getUser(httpServletRequest: HttpServletRequest): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iUserService.getUser(httpServletRequest), HttpStatus.OK.value())
    }

    @GetMapping("/userId/{change}")
    fun getUserByUserId(@PathVariable("change") change: Long): ResponseEntity<BaseResponse> {
        loggerFactory.info("getUserByUserId in UserController: $change")
        return getSuccessObject(this.iUserService.getUserByUserId(change), HttpStatus.OK.value())
    }

    @GetMapping("/bulk-user-data")
    fun getBulkUserData(@RequestBody userIds: List<Long>): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iUserService.getBulkUserData(userIds), HttpStatus.OK.value())
    }

    @PostMapping("/forgot-password")
    fun forgotPassword(@RequestBody usernameDTO: UsernameDTO): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iUserService.forgotPassword(usernameDTO.username!!), HttpStatus.OK.value())
    }

    @PostMapping("/update-password")
    fun updatePassword(
        @RequestBody forgotPasswordDTO: ForgotPasswordDTO
    ): ResponseEntity<BaseResponse> {
        return getSuccessObject(this.iUserService.updatePassword(forgotPasswordDTO), HttpStatus.OK.value())
    }

    @GetMapping("/is-service-up")
    fun isServiceUp(): ResponseEntity<BaseResponse> {
        loggerFactory.info("isServiceUp without value")
        val value = this.restTemplate.getForObject(
            "http://localhost:8081/team-games-management-service/team/is-service-up",
            SuccessResponseDTO::class.java
        )
        loggerFactory.info("isServiceUp in UserController: $value")

        return getSuccessObject("yes this service is up", HttpStatus.OK.value())
    }

    @PostMapping("/search-for-users")
    fun searchForUsers(@RequestBody searchUserDTO: SearchUserDTO): ResponseEntity<BaseResponse> {
        this.loggerFactory.info("searchForUsers in UserController")
        return getSuccessObject(this.iUserService.searchUserByAnyParameter(searchUserDTO), HttpStatus.OK.value());
    }

    @PostMapping("/send-friend-request")
    fun sendFriendRequest(
        @RequestBody friendResponseDTO: FriendRequestDTO,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        this.loggerFactory.info("sendFriendRequest in UserController")
        this.iUserService.sendFriendRequest(friendResponseDTO, request)
        return getSuccessResponse()
    }

    @PostMapping("/delete-sent-request")
    fun deleteSentRequest(
        @RequestBody friendResponseDTO: FriendRequestDTO,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        this.loggerFactory.info("deleteSentRequest in UserController")
        this.iUserService.acceptOrDenyFriendRequest(friendResponseDTO, request, RequestStatusType.FRIEND_REQUEST_DENIED)
        return getSuccessResponse()
    }

    @PostMapping("/accept-friend-request")
    fun acceptFriendRequest(
        @RequestBody friendResponseDTO: FriendRequestDTO,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        this.loggerFactory.info("acceptFriendRequest in UserController")
        this.iUserService.acceptOrDenyFriendRequest(
            friendResponseDTO,
            request,
            RequestStatusType.FRIEND_REQUEST_ACCEPTED
        )
        return getSuccessResponse()
    }

    @GetMapping("/get-all-friends")
    fun getAllFriends(
        @RequestParam("username") username: String,
        request: HttpServletRequest
    ): ResponseEntity<BaseResponse> {
        this.loggerFactory.info("getAllFriends in UserController")
        return getSuccessObject(this.iUserService.getAllFriends(username, request), HttpStatus.OK.value())
    }

    @GetMapping("/get-all-sent-requests")
    fun getAllSentRequests(
        @RequestParam("username") username: String,
        request: HttpServletRequest
    ): ResponseEntity<BaseResponse> {
        this.loggerFactory.info("getAllSentRequests in UserController")
        return getSuccessObject(this.iUserService.getAllSentRequest(username, request), HttpStatus.OK.value())
    }

    @GetMapping("/get-user-details")
    fun getUserDetails(@RequestParam("username") username: String): ResponseEntity<BaseResponse> {
        this.loggerFactory.info("getUserDetails in UserController")
        return getSuccessObject(this.iUserService.getUserDetails(username), HttpStatus.OK.value())
    }

    @GetMapping("/request-status")
    fun getRequestStatus(
        @RequestParam("username") username: String,
        request: HttpServletRequest
    ): ResponseEntity<BaseResponse> {
        this.loggerFactory.info("getRequestStatus in UserController")
        return getSuccessObject(this.iUserService.getRequestStatus(username, request), HttpStatus.OK.value())
    }

    @PostMapping("/upload-profile-image")
    fun uploadProfileImage(@RequestBody uploadImageDTO: UploadImageDTO): ResponseEntity<BaseResponse> {
        this.loggerFactory.info("uploadProfileImage in UserController")
        return getSuccessObject(this.iUserService.uploadProfileImage(uploadImageDTO), HttpStatus.OK.value())
    }
}