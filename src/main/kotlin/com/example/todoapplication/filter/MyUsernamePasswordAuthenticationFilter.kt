package com.example.todoapplication.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.todoapplication.LoginForm
import com.example.todoapplication.model.MyUserDetails
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*

class MyUsernamePasswordAuthenticationFilter(private val authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {
    init {
        setRequiresAuthenticationRequestMatcher { request ->
            request.method == "POST" && request.requestURI == "/api/login"
        }

        setAuthenticationSuccessHandler { request, response, authentication ->
            val issueAt = Date()
            // JWT作成
            val jwt: String =
                JWT.create()
                    .withIssuer("test-issue")
                    .withIssuedAt(issueAt)
                    .withExpiresAt(Date(issueAt.time + 1000 * 60 * 60))
                    .withClaim("username", authentication.name)
                    .withClaim("role", authentication.authorities.iterator().next().toString())
                    .sign(Algorithm.HMAC256("secret"))
            response.setHeader("X-AUTH-TOKEN", jwt)
            response.status = 200

            val user: MyUserDetails = (SecurityContextHolder.getContext().authentication.principal) as? MyUserDetails
                ?: throw UsernameNotFoundException("User not found")
            response.writer.write(ObjectMapper().writeValueAsString(user))
        }
    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val principal: LoginForm = ObjectMapper().readValue(request?.inputStream, LoginForm::class.java)
        return authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(principal.username, principal.password)
        )
    }
}