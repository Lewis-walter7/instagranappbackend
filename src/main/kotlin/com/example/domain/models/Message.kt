package com.example.domain.models

import com.example.data.services.LocalDateSerializer
import com.example.domain.models.Posts.default
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime



@Serializable
data class Message(
    val text: String,
    val senderId: String,
    val resourceUrl: Media,
    val receiverId: String,
    val createdAt: Long
)

object Messages: UUIDTable() {
    val postUrl = varchar("postUrl",512)
    val createdAt = long("created_at")

    val userId = reference("id", Users.id, onDelete = ReferenceOption.CASCADE)
}
