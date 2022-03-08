package com.hu.fypimplbackend.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.hu.fypimplbackend.config.ApplicationConfig
import com.hu.fypimplbackend.dto.TokensDTO
import org.apache.http.HttpHeaders
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.userdetails.User as SpringSecurityUser

data class JWTDecodedData(
    val token: String,
    val subject: String,
    val claims: Array<String>,
    val iat: String,
    val exp: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JWTDecodedData

        if (token != other.token) return false
        if (subject != other.subject) return false

        return true
    }

    override fun hashCode(): Int {
        var result = token.hashCode()
        result = 31 * result + subject.hashCode()
        return result
    }
}

@Component
class JwtUtil(
    @Autowired
    private val applicationConfig: ApplicationConfig,

    @Autowired
    private val loggerFactory: Logger
) {
    private final val algorithm: Algorithm = Algorithm.HMAC256(this.applicationConfig.jwtSecretKey.toByteArray())
    private final val jwtVerifier: JWTVerifier = JWT.require(algorithm).build()

    fun getDecodedToken(completeHeaderString: String): JWTDecodedData? {
        return if (completeHeaderString.startsWith("Bearer ")) {
            val tokenString = completeHeaderString.substringAfter("Bearer ")
            try {
                val decodedJWT = jwtVerifier.verify(tokenString)
                return JWTDecodedData(
                    tokenString,
                    decodedJWT.subject,
                    decodedJWT.getClaim("roles").asArray(String::class.java),
                    decodedJWT.issuedAt.toString(),
                    decodedJWT.expiresAt.toString()
                )
            } catch (exception: Exception) {
                this.loggerFactory.error("getDecodedToken in JwtUtil: ${exception.message}")
            }
            null
        } else {
            null
        }
    }

    fun getDecodedToken(request: HttpServletRequest): JWTDecodedData? {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if (authorizationHeader != null) this.getDecodedToken(authorizationHeader) else null
    }

    fun createTokenDTO(user: SpringSecurityUser, request: HttpServletRequest): TokensDTO {
        val accessToken = JWT.create()
            .withSubject(user.username)
            .withIssuedAt(Date(System.currentTimeMillis()))
            .withExpiresAt(Date(System.currentTimeMillis() + (A_DAY_VALIDITY)))
            .withIssuer(request.requestURL.toString())
            .withClaim("roles", user.authorities.map { it.authority })
            .sign(algorithm)

        val refreshToken = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + (SEVEN_DAYS_VALIDITY)))
            .withIssuedAt(Date(System.currentTimeMillis()))
            .withIssuer(request.requestURL.toString())
            .sign(algorithm)
        return TokensDTO(accessToken, refreshToken)
    }

    companion object {
        const val SEVEN_DAYS_VALIDITY = 7 * 24 * 60 * 60 * 1000
        const val A_DAY_VALIDITY = 24 * 60 * 60 * 1000
    }
}