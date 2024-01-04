package com.example.domain.dao.user

import com.example.domain.models.User
import java.util.UUID

interface UserDao {
    suspend fun createUser(user: User): User
    suspend fun findUserById(id: UUID): User?
    suspend fun findUserbyUsername(username: String): User?
}
