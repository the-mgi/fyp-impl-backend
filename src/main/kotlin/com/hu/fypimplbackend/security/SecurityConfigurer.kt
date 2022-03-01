package com.hu.fypimplbackend.security

import com.hu.fypimplbackend.security.filters.CustomAuthenticationFilter
import com.hu.fypimplbackend.security.filters.JWTRequestFilter
import com.hu.fypimplbackend.services.IUserService
import com.hu.fypimplbackend.utility.OPEN_ROUTES
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class SecurityConfigurer(
    @Autowired
    private val iUserService: IUserService,

    @Autowired
    private val passswordEncoder: PasswordEncoder,

    @Autowired
    private val jwtUtil: JwtUtil,

    @Autowired
    private val jwtRequestFilter: JWTRequestFilter

) : WebSecurityConfigurerAdapter() {
    override fun configure(authenticationManagerBuiilder: AuthenticationManagerBuilder) {
        authenticationManagerBuiilder.userDetailsService(this.iUserService).passwordEncoder(this.passswordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.csrf().disable()
            .authorizeRequests().antMatchers(*OPEN_ROUTES).permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.addFilter(
            CustomAuthenticationFilter(
            super.authenticationManagerBean(),
            this.jwtUtil
        ).apply { setFilterProcessesUrl("/user/login") })
        http.addFilterBefore(jwtRequestFilter, CustomAuthenticationFilter::class.java)
    }

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint {
        return CustomAuthenticationEntryPoint()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}