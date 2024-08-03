package com.example.todoapplication.filter

import org.slf4j.Logger
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

class MyRequestHeaderAuthenticationFilter(private val authenticationManager: AuthenticationManager) : RequestHeaderAuthenticationFilter() {
    init {
        setPrincipalRequestHeader("Authorization")
        setExceptionIfHeaderMissing(false)
        setAuthenticationManager(authenticationManager)
        setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher("/api/users"))

        setAuthenticationSuccessHandler { request, response, authentication ->
            logger.debug("Authentication success: $authentication")
        }

        setAuthenticationFailureHandler { request, response, exception ->
            logger.debug("Authentication failure: $exception")
        }
    }
}