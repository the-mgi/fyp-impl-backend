package com.hu.fypimplbackend.security.filters

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.hu.fypimplbackend.dto.response.ErrorResponseDTO
import com.hu.fypimplbackend.utility.ObjectMapperSingleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter : OncePerRequestFilter() {
    private val loggerFactory: Logger = LoggerFactory.getLogger(CustomAuthenticationFilter::class.java)
    private val objectMapper = ObjectMapperSingleton.objectMapper

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.servletPath == "/api/login") {
            filterChain.doFilter(request, response)
            return
        }
        val authorizationHeader = request.getHeader(AUTHORIZATION)
        loggerFactory.info("authorizationHeader $authorizationHeader")
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                val token = authorizationHeader.substringAfter("Bearer ")
                val algorithm = Algorithm.HMAC256("secret".toByteArray())
                val jwtVerifier = JWT.require(algorithm).build()
                val decodedJWT = jwtVerifier.verify(token)

                val username = decodedJWT.subject
                val roles = decodedJWT.getClaim("roles").asArray(String::class.java)

                val authorities: MutableList<SimpleGrantedAuthority> = mutableListOf()
                roles.forEach { authorities.add(SimpleGrantedAuthority(it)) }

                val usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken(username, null)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                filterChain.doFilter(request, response)
            } catch (exception: Exception) {
                loggerFactory.error("doFilterInternal in CustomAuthorizationFilter ${exception.message}")
                response.contentType = APPLICATION_JSON_VALUE
                this.objectMapper.writeValue(
                    response.outputStream,
                    ErrorResponseDTO(
                        "Username password combination does not exist",
                        "Forbidden",
                        HttpStatus.FORBIDDEN.value()
                    )

                )
            }
        } else {
            filterChain.doFilter(request, response)
        }
    }
}