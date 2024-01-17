package com.example.domain.requests

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val userId: String,
    val postUrl: String,
    val showComments: Boolean = true,
    val hideLikes: Boolean = false,
    val caption: String? = null
)