package com.hu.fypimplbackend.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonConfig(
    @Autowired
    private val applicationConfig: AWSApplicationConfig
) {
    @Bean
    fun s3(): AmazonS3 {
        val awsCredentials: AWSCredentials =
            BasicAWSCredentials(applicationConfig.accessKey, applicationConfig.secretKey)

        return AmazonS3ClientBuilder
            .standard()
            .withRegion(applicationConfig.region)
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .build()
    }
}