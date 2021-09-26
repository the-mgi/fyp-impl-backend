package com.hu.fypimplbackend.security

import com.hu.fypimplbackend.security.filters.CustomAuthenticationFilter
import com.hu.fypimplbackend.utility.JWTAuthenticationEntryPoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    @Autowired
    private val userDetailsService: UserDetailsService,

    @Autowired
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,

    @Autowired
    private val jwtAuthenticationEntryPoint: JWTAuthenticationEntryPoint

) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests().antMatchers("/user/save", "/login", "/swagger-ui.html", "/swagger-ui/**", "/v3/**", "/miscellaneous/**").permitAll()

        http.authorizeRequests().anyRequest().authenticated()
//        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/**").hasAnyAuthority(RoleTypes.ROLE_USER.name)
//        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/save/**").hasAnyAuthority(RoleTypes.ROLE_ADMIN.name)
        http.addFilter(CustomAuthenticationFilter(authenticationManagerBean()))
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManager()
    }
}