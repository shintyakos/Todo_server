package com.example.todoapplication.model

import java.util.*

data class UserData(
    val id: UUID?,
    val username: String,
    val password: String,
    val email: String
)
