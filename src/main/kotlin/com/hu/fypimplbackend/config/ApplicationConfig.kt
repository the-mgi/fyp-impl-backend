package com.hu.fypimplbackend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties("com.hu.amazon.aws")
@PropertySource("classpath:application.properties")
data class ApplicationConfig(
    @Value("\${com.hu.amazon.aws.secretKey}")
    val secretKey: String,

    @Value("\${com.hu.amazon.aws.accessKey}")
    val accessKey: String,

    @Value("\${com.hu.amazon.aws.region}")
    val region: String,

    @Value("\${com.hu.amazon.aws.profileImageBucket}")
    val profileImageBucket: String
)

