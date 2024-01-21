package com.example.data.databaseopertaions

import com.example.data.services.HashingService
import com.example.data.services.JWTService
import com.example.domain.dao.user.UserDao
import com.example.domain.models.User
import com.example.domain.requests.EditedUserRequest
import com.example.domain.requests.FollowRequests
import com.example.domain.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class UserService(
    private val userRepository: UserDao
){
    suspend fun createUser(user: User): UserResponse? {
        if (user.username.isBlank() || user.password.isBlank()) {
            return null
        }
        val hashedPassword = HashingService.hashPassword(user.password)
        val newUser = User(
            username = user.username,
            password = hashedPassword,
            createdAt = user.createdAt,
            followerCount = 0,
            followingCount = 0,
            postCount = 0
        )
        return userRepository.createUser(newUser)
    }

    suspend fun loginUser(username: String, password: String): UserResponse? {
        val user = userRepository.findUserbyUsername(username)
        return if(user != null) {
            if (HashingService.verifyPassword(password, user)) {
                user
            } else {
                null
            }
        } else null
    }

    suspend fun updateUser(userRequest: EditedUserRequest) {
        userRepository.editUser(userRequest)
    }

    suspend fun updateUserWithUsername(userRequest: EditedUserRequest): String{
        return withContext(Dispatchers.IO) {
            userRepository.editUser(userRequest)
            val user = userRepository.findUserById(UUID.fromString(userRequest.id))
            JWTService.generateToken(user!!)
        }
    }

    suspend fun getUsersByUser(username: String): List<UserResponse>{
        return withContext(Dispatchers.IO) {
            userRepository.getUsersByUser(username)
        }
    }

    suspend fun followUser(followRequests: FollowRequests): Unit {
        return withContext(Dispatchers.IO) {
            userRepository.followUser(followRequests)
        }
    }

    suspend fun getFollowerCount(id: UUID): Long{
        return userRepository.getFollowerCount(id)
    }

    suspend fun isFollowing(followRequests: FollowRequests): Boolean{
        return userRepository.isFollowing(followRequests)
    }
}