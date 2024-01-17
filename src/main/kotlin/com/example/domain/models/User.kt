package com.example.domain.models


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.UUIDTable

@Serializable
data class User(
    val username: String,
    val password: String,
    val email: String? = null,
    val phoneNumber: String? = null,
    val profileImage: String? = null,
    val bio: String? = null,
    val followers: List<String> = emptyList(),
    val createdAt: Long,
    val accountType: String = AccountType.PUBLIC.toString()
)

object Users: UUIDTable() {
    val username = varchar("username", 45).uniqueIndex()
    val password = varchar("password", 128)
    val profileImage = varchar("profile_image", 512).nullable()
    val accountType = varchar("account_type", 10).default(AccountType.PUBLIC.toString())
    val email = varchar("email", 50).nullable()
    val phoneNumber = varchar("phone_number", 16).nullable()
    val bio = varchar("bio", 512).nullable()
    val createdAt = long("created_at")
}