package com.example.todoapplication.services

import com.example.todoapplication.model.MyUserDetails
import com.example.todoapplication.model.UserData
import com.example.todoapplication.repository.IUserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
@Transactional
class UserService: UserDetailsService {
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

    fun getUser(username: String): UserData {
        return userRepository.findByUserName(username).let {
            UserData(
                id = it.id,
                username = it.name,
                password = it.password,
                email = it.email
            )
        }
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByUserName(username ?: "")
        val userDetails = MyUserDetails(
            username = user.email,
            password = user.password,
            authorities = AuthorityUtils.createAuthorityList("ROLE_USER")
        )
        return userDetails
    }
}
