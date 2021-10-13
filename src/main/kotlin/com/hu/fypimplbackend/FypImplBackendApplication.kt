package com.hu.fypimplbackend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableEurekaClient
class FypImplBackendApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(FypImplBackendApplication::class.java, *args)
        }
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    @LoadBalanced
    fun getRestTemplate() = RestTemplate()
}
