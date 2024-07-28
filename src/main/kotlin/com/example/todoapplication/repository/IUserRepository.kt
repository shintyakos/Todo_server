package com.example.todoapplication.repository

import com.example.todoapplication.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IUserRepository: JpaRepository<Users, UUID>
