package com.hu.fypimplbackend.security.filters

import com.hu.fypimplbackend.dto.EmailPasswordDTO
import com.hu.fypimplbackend.dto.response.ErrorResponseDTO
import com.hu.fypimplbackend.dto.response.SuccessResponseDTO
import com.hu.fypimplbackend.security.JwtUtil
import com.hu.fypimplbackend.utility.ObjectMapperSingleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.userdetails.User as SpringSecurityUser

class CustomAuthenticationFilter(
    private val authenticationManagerSpring: AuthenticationManager,
    private val jwtUtil: JwtUtil

) : UsernamePasswordAuthenticationFilter() {
    private val objectMapper = ObjectMapperSingleton.objectMapper
    private val loggerFactory: Logger = LoggerFactory.getLogger(CustomAuthenticationFilter::class.java)

    @Throws(IOException::class)
    private fun getUserNameAndPassword(request: HttpServletRequest): EmailPasswordDTO {
        val body = request.reader.lines().collect(Collectors.joining())
        return objectMapper.readValue(body, EmailPasswordDTO::class.java)
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val usernameAndPassword: EmailPasswordDTO = getUserNameAndPassword(request)
        val emailAddress = usernameAndPassword.emailAddress
        val password = usernameAndPassword.password

        loggerFactory.info("emailAddress: $emailAddress and password: $password")
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(emailAddress, password)
        return this.authenticationManagerSpring.authenticate(usernamePasswordAuthenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val user: SpringSecurityUser = authResult.principal as SpringSecurityUser
        val tokens = this.jwtUtil.createTokenDTO(user, request)
        response.contentType = APPLICATION_JSON_VALUE
        this.objectMapper.writeValue(response.outputStream, SuccessResponseDTO(tokens, HttpStatus.OK.value()))
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException
    ) {
        response.contentType = APPLICATION_JSON_VALUE
        response.status = HttpStatus.FORBIDDEN.value()
        this.objectMapper.writeValue(
            response.outputStream,
            ErrorResponseDTO("Email and password combination does not exist", "Forbidden", HttpStatus.FORBIDDEN.value())
        )
    }
}