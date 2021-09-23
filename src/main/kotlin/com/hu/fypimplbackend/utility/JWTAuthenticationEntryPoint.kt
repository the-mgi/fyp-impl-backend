package com.hu.fypimplbackend.utility

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTAuthenticationEntryPoint : AuthenticationEntryPoint {
    private val objectMapper = ObjectMapperSingleton.objectMapper

    override fun commence(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, authenticationException: AuthenticationException) {

    }
}