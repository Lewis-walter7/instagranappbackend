package com.example.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: String,
    val username: String,
    val password: String,
    val profileImage: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val bio: String? = null,
    val createdAt: Long?,
    val accountType: String
)
