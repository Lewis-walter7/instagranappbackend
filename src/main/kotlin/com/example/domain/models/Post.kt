package com.example.domain.models

import com.example.data.services.UUIDSerializerService
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.*

@Serializable
data class Media(
    val type: MediaType,
    val url: String
)


@Serializable
data class Post(
    val caption: String? = "",
    val postUrl: String,
    val likes: Long = 0,
    val createdAt: Long,
    @Serializable(with = UUIDSerializerService::class)
    val userId: UUID,
    val showComments: Boolean,
    val hideLikes: Boolean
)

object Posts: UUIDTable() {
    val postUrl = varchar("postUrl",512)
    val createdAt = long("createdAt").default(System.currentTimeMillis())
    val likes = long("likes").default(0)
    val showComments = bool("show_comments").default(true)
    val hideLikes = bool("hide_likes").default(true)

    val caption = varchar("caption", 1000).nullable()
    val userId = reference("user_id", Users.id, onDelete = ReferenceOption.CASCADE)
}