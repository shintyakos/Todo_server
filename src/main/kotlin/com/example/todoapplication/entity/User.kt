package com.example.todoapplication.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue
    @Column(name = "id")
    var id: UUID? = null,

    @Column(name = "username")
    var name: String = "",

    @Column(name = "password")
    var password: String = "",

    @Column(name = "email")
    var email: String = "",

    @Column(name = "created_at")
    var createdAt: LocalDate = LocalDate.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDate = LocalDate.now(),

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false
)
