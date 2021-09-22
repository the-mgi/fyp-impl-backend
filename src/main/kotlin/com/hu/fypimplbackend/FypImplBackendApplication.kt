package com.hu.fypimplbackend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class FypImplBackendApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(FypImplBackendApplication::class.java, *args)
        }
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
