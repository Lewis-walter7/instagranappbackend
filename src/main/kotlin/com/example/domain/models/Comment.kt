package com.example.domain.models

import com.example.domain.models.Posts.default
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

data class Comment(
    val id: String,
    val body: String,
    val userId: String,
    val likes: List<String>,
    val createdAt: Long
)


object Comments: Table(){
    val id = varchar("id", 512).uniqueIndex()
    val body = varchar("body",10000)
    val created_at = long("created_at").default(System.currentTimeMillis())

    //one-many relationship with the user
    val userId = reference("id", Users.id, onDelete = ReferenceOption.CASCADE)
    val postId = reference("id", Posts.id, onDelete = ReferenceOption.CASCADE)
}