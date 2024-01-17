package com.example.domain.dao.user

import com.example.domain.models.User
import com.example.domain.response.UserResponse
import java.util.UUID

interface UserDao {
    suspend fun createUser(user: User): UserResponse
    suspend fun findUserById(id: UUID): UserResponse?
    suspend fun findUserbyUsername(username: String): UserResponse?
}
