package com.hu.fypimplbackend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("com.hu.app")
@PropertySource("classpath:application.properties")
data class ApplicationConfig(
    @Value("\${com.hu.app.jwt.secret-key}")
    val jwtSecretKey: String,

    @Value("\${com.hu.app.from-email}")
    val fromEmail: String,

    @Value("\${com.hu.app.from-password}")
    val fromPassword: String
)