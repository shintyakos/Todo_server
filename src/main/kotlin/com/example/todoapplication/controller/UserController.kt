package com.example.todoapplication.controller

import com.example.todoapplication.model.UserData
import com.example.todoapplication.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/users")
    fun getAllUsers(): List<UserData> = userService.getUsers()
}
