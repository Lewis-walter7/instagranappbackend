package com.example.domain.requests

import kotlinx.serialization.Serializable

@Serializable
data class EditedUserRequest(
    val id: String,
    val username: String,
    val name: String? = null,
    val bio: String? = null,
    val profileImage: String? = null,
    val requestedUsername: String? = null
)