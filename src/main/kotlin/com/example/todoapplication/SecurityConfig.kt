package com.example.todoapplication

import com.example.todoapplication.filter.MyAuthenticationUserDetailService
import com.example.todoapplication.filter.MyRequestHeaderAuthenticationFilter
import com.example.todoapplication.filter.MyUsernamePasswordAuthenticationFilter
import com.example.todoapplication.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    fun configureProvider(
        auth: AuthenticationManagerBuilder,
        myUserDetailsService: UserService,
        myAuthenticationUserDetailService: MyAuthenticationUserDetailService
    ) {
        val preAuthenticatedAuthenticationProvider = PreAuthenticatedAuthenticationProvider()
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(myAuthenticationUserDetailService)
        preAuthenticatedAuthenticationProvider.setUserDetailsChecker(AccountStatusUserDetailsChecker())
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider)

        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setUserDetailsService(userService)
        daoAuthenticationProvider.setPasswordEncoder(BCryptPasswordEncoder(8))
        auth.authenticationProvider(daoAuthenticationProvider)
    }

    @Bean
    fun configureHttpSecurity(
        http: HttpSecurity,
        authenticationManager: AuthenticationManager
    ): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/hello").permitAll()
                    .requestMatchers("/api/login").permitAll()
            }
            .csrf { csrf -> csrf.disable() }
            .addFilter(MyUsernamePasswordAuthenticationFilter(authenticationManager))
            .addFilter(MyRequestHeaderAuthenticationFilter(authenticationManager))
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(8)
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }
}