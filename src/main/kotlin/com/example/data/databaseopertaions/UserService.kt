package com.example.data.databaseopertaions

import com.example.data.services.HashingService
import com.example.domain.dao.user.UserDao
import com.example.domain.models.User
import com.example.domain.response.UserResponse

class UserService(
    private val userRepository: UserDao
){
    suspend fun createUser(user: User): UserResponse? {
        if (user.username.isBlank() || user.password.isBlank()) {
            return null
        }
        val hashedPassword = HashingService.hashPassword(user.password)
        val newUser = User(
            username = user.username,
            password = hashedPassword,
            createdAt = user.createdAt
        )
        return userRepository.createUser(newUser)
    }

    suspend fun loginUser(username: String, password: String): UserResponse? {
        val user = userRepository.findUserbyUsername(username)
        return if(user != null) {
            if (HashingService.verifyPassword(password, user)) {
                user
            } else {
                null
            }
        } else null
    }
}