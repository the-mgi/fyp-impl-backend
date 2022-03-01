package com.hu.fypimplbackend.security

import com.hu.fypimplbackend.dto.response.ErrorResponseDTO
import com.hu.fypimplbackend.utility.ObjectMapperSingleton
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authenticationException: AuthenticationException
    ) {
        response.contentType = APPLICATION_JSON_VALUE
        response.status = HttpStatus.FORBIDDEN.value()
        ObjectMapperSingleton.objectMapper.writeValue(
            response.outputStream,
            ErrorResponseDTO(
                "Access Forbidden",
                authenticationException.message,
                HttpStatus.FORBIDDEN.value()
            )
        )
    }
}