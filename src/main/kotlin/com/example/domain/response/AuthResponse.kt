package com.example.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String? = null,
    val message: String? = null
)