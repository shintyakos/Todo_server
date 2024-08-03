package com.example.todoapplication.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.interfaces.DecodedJWT
import com.example.todoapplication.model.MyUserDetails
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Service

@Service
class MyAuthenticationUserDetailService : AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    override fun loadUserDetails(token: PreAuthenticatedAuthenticationToken?): UserDetails {
        var decodedJWT: DecodedJWT? = null

        try {
            decodedJWT = JWT.require(Algorithm.HMAC256("secret")).build().verify(token?.principal.toString());
        } catch (error: JWTDecodeException) {
            throw BadCredentialsException("Authorization token is invalid")
        }

        if (decodedJWT.token.isEmpty()) {
            throw UsernameNotFoundException("Token is empty")
        }

        val user = MyUserDetails(
            username = decodedJWT.getClaim("username").asString(),
            password = "",
            authorities = AuthorityUtils.createAuthorityList(decodedJWT.getClaim("role").asString())
        )

        return user
    }
}