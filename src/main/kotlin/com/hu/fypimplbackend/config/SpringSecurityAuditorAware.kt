package com.hu.fypimplbackend.config
//
//import com.hu.fypimplbackend.domains.User
//import org.springframework.data.domain.AuditorAware
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.context.SecurityContext
//import org.springframework.security.core.context.SecurityContextHolder
//import java.util.*
//
//class SpringSecurityAuditorAware : AuditorAware<User> {
//
//    override fun getCurrentAuditor(): Optional<User> {
//        return Optional.ofNullable(SecurityContextHolder.getContext())
//            .map(SecurityContext::getAuthentication)
//            .filter(Authentication::isAuthenticated)
//            .map(Authentication::getPrincipal)
//            .map(User::class.java::cast)
//    }
//}