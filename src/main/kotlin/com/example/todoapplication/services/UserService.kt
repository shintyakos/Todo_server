package com.example.todoapplication.services

import com.example.todoapplication.model.UserData
import com.example.todoapplication.repository.IUserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService {
    @Autowired
    private lateinit var userRepository: IUserRepository

    fun getUsers(): List<UserData> {
        return userRepository.findAll().map {
            UserData(
                id = it.id,
                username = it.name,
                password = it.password,
                email = it.email
            )
        }
    }
}
