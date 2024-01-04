package com.example.domain.models

import com.example.data.services.LocalDateSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

@Serializable
data class Media(
    val type: MediaType,
    val url: String
)


@Serializable
data class Post(
    val caption: String? = "",
    val media: Media,
    val likes: List<String>?,
    val comments: List<String>?,
    val createdAt: Long
)

object Posts: UUIDTable() {
    val postUrl = varchar("postUrl",512)
    val comment = varchar("comment", 151)
    val createdAt = long("createdAt").default(System.currentTimeMillis())

    val userId = reference("id", Users.id, onDelete = ReferenceOption.CASCADE)
}