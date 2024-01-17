package com.example.domain.models

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption

object Followers: UUIDTable() {
    val followerId = reference("follower_id", Users, onDelete = ReferenceOption.CASCADE)
    val followingId = reference("following_id", Users, onDelete = ReferenceOption.CASCADE)
}