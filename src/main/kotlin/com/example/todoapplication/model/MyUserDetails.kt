package com.example.todoapplication.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class MyUserDetails(
    username: String,
    password: String,
    authorities: MutableCollection<GrantedAuthority>?
): User(username, password, authorities)
