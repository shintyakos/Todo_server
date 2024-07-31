package com.example.todoapplication.repository

import com.example.todoapplication.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IUserRepository: JpaRepository<Users, UUID> {
    @Query(value = "SELECT * FROM users WHERE users.username = :username", nativeQuery = true)
    fun findByUserName(@Param("username") username: String): Users
}
