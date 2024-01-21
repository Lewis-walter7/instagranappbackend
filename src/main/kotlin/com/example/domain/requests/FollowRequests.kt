package com.example.domain.requests

import com.example.data.services.UUIDSerializerService
import kotlinx.serialization.Serializable
import java.util.*

//both follow and unfollow requests
@Serializable
data class FollowRequests(
    @Serializable(with = UUIDSerializerService::class)
    val followerId: UUID,
    @Serializable(with = UUIDSerializerService::class)
    val followingId: UUID
)