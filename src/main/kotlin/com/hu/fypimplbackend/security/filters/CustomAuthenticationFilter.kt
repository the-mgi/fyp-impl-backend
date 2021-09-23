package com.hu.fypimplbackend.security.filters

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.hu.fypimplbackend.dto.user.UsernameAndPasswordDTO
import com.hu.fypimplbackend.utility.ObjectMapperSingleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import java.util.stream.Collectors
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.userdetails.User as SpringSecurityUser

class CustomAuthenticationFilter(
    private val authenticationManagerSpring: AuthenticationManager

) : UsernamePasswordAuthenticationFilter() {
    private val objectMapper = ObjectMapperSingleton.objectMapper
    private val loggerFactory: Logger = LoggerFactory.getLogger(CustomAuthenticationFilter::class.java)

    @Throws(IOException::class)
    private fun getUserNameAndPassword(request: HttpServletRequest): UsernameAndPasswordDTO {
        val body = request.reader.lines().collect(Collectors.joining())
        return objectMapper.readValue(body, UsernameAndPasswordDTO::class.java)
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val usernameAndPassword: UsernameAndPasswordDTO = getUserNameAndPassword(request)
        val username = usernameAndPassword.username
        val password = usernameAndPassword.password

        loggerFactory.info("username: $username and password: $password")
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(username, password)
        return this.authenticationManagerSpring.authenticate(usernamePasswordAuthenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val user: SpringSecurityUser = authResult.principal as SpringSecurityUser
        val algorithm = Algorithm.HMAC256("secret".toByteArray())
        val accessToken = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + (10 * 60 * 1000)))
            .withIssuer(request.requestURL.toString())
            .withClaim("roles", user.authorities.map { it.authority })
            .sign(algorithm)

        val refreshToken = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + (7 * 24 * 60 * 60 * 1000)))
            .withIssuer(request.requestURL.toString())
            .sign(algorithm)

        val map = HashMap<String, String>()
        map["access_token"] = accessToken
        map["refresh_token"] = refreshToken
        response.contentType = APPLICATION_JSON_VALUE
        this.objectMapper.writeValue(response.outputStream, map)
    }
}