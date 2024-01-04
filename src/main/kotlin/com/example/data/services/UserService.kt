package com.example.data.services

import com.example.domain.dao.user.UserDao
import com.example.domain.models.User
import java.time.LocalDateTime

class UserService(
    private val userRepository: UserDao
){
    suspend fun createUser(user: User): User? {
        if (user.username.isBlank() || user.password.isBlank()) {
            return null
        }
        val hashedPassword = HashingService.hashPassword(user.password)
        val newUser = User(
            username = user.username,
            password = hashedPassword,
            createdAt = System.currentTimeMillis(),
        )
        return userRepository.createUser(newUser)
    }

    suspend fun loginUser(username: String, password: String): Boolean {
        val user = userRepository.findUserbyUsername(username)
        if (user?.let {
                if (HashingService.verifyPassword(password, it)) {
                    true
                } else {
                    false
                }
            } == true) return true
        else throw AssertionError("Wrong Password")
    }
}