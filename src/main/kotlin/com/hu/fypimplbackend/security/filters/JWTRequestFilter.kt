package com.hu.fypimplbackend.security.filters

import com.hu.fypimplbackend.dto.response.ErrorResponseDTO
import com.hu.fypimplbackend.security.JwtUtil
import com.hu.fypimplbackend.utility.OPEN_ROUTES
import com.hu.fypimplbackend.utility.ObjectMapperSingleton
import org.apache.http.HttpHeaders
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTRequestFilter(
    @Autowired
    private val jwtUtil: JwtUtil,

    @Autowired
    private val loggerFactory: Logger
) : OncePerRequestFilter() {
    private val objectMapper = ObjectMapperSingleton.objectMapper

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.servletPath in OPEN_ROUTES) {
            filterChain.doFilter(request, response)
            return
        }
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        this.loggerFactory.info("authorizationHeader $authorizationHeader")
        if (authorizationHeader != null) {
            try {
                val jwtDecodedData = this.jwtUtil.getDecodedToken(authorizationHeader)
                    ?: throw RuntimeException("Invalid Authorization Header")
                val authorities: List<SimpleGrantedAuthority> = jwtDecodedData.claims.map { SimpleGrantedAuthority(it) }
                val usernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken(jwtDecodedData.subject, null, authorities)
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                filterChain.doFilter(request, response)
            } catch (exception: Exception) {
                this.loggerFactory.error("doFilerInternal in JWTRequestFilter")
                response.contentType = APPLICATION_JSON_VALUE
                response.status = HttpStatus.FORBIDDEN.value()
                this.objectMapper.writeValue(
                    response.outputStream, ErrorResponseDTO(
                        "Access Forbidden",
                        "Invalid Authorization Header",
                        HttpStatus.FORBIDDEN.value()
                    )
                )
            }
        } else {
            response.contentType = APPLICATION_JSON_VALUE
            response.status = HttpStatus.FORBIDDEN.value()
            this.objectMapper.writeValue(
                response.outputStream, ErrorResponseDTO(
                    "Access Forbidden",
                    "Authorization Bearer Not Specified",
                    HttpStatus.FORBIDDEN.value()
                )
            )
        }
    }
}