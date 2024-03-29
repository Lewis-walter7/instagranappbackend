package com.example.domain.dao.user

import com.example.domain.models.User
import com.example.domain.requests.EditedUserRequest
import com.example.domain.requests.FollowRequests
import com.example.domain.response.UserResponse
import java.util.UUID

interface UserDao {
    suspend fun createUser(user: User): UserResponse
    suspend fun findUserById(id: UUID): UserResponse?
    suspend fun findUserbyUsername(username: String): UserResponse?

    suspend fun editUser(user: EditedUserRequest)
    suspend fun getUsersByUser(username: String): List<UserResponse>

    suspend fun followUser(followRequests: FollowRequests): Unit
    fun getFollowerCount(id: UUID): Long
    fun getFollowingCount(id: UUID): Long
    suspend fun isFollowing(followRequests: FollowRequests): Boolean
}
