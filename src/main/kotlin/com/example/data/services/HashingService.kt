package com.example.data.services

import com.example.domain.response.UserResponse
import org.mindrot.jbcrypt.BCrypt

object HashingService {
    fun hashPassword(password: String): String {
        val salt = BCrypt.gensalt()
        return BCrypt.hashpw(password, salt)
    }

    fun verifyPassword(password: String, user: UserResponse): Boolean {
        return BCrypt.checkpw(password, user.password)
    }
}