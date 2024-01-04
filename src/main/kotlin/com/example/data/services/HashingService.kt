package com.example.data.services

import com.example.domain.models.User
import org.jetbrains.exposed.sql.Column
import org.mindrot.jbcrypt.BCrypt

object HashingService {
    fun hashPassword(password: String): String {
        val salt = BCrypt.gensalt()
        return BCrypt.hashpw(password, salt)
    }

    fun verifyPassword(password: String, user: User): Boolean {
        return BCrypt.checkpw(password, user.password)
    }
}