package com.example.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationResponse(
    val isAuthenticated: Boolean
)