package com.example.domain.response

import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val postUrl: String,
    val userId: String,
    val showComments: Boolean,
    val hideLikes: Boolean,
    val createdAt: Long,
    val likes: Long,
    val id: String
)
