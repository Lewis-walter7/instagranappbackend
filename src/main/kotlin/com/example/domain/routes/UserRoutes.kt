package com.example.domain.routes

import com.example.data.services.JWTService
import com.example.data.databaseopertaions.UserService
import com.example.domain.models.User
import com.example.domain.requests.EditedUserRequest
import com.example.domain.requests.UserRequest
import com.example.domain.response.AuthResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.IOException

fun Route.UserRoutes(userService: UserService) {
    get("/"){
        call.respond("Home")
    }
    post("login") {
        val userRequest = call.receive<UserRequest>()
        val userResponse = userService.loginUser(userRequest.username, userRequest.password)
        if (userResponse != null) {
            val token = JWTService.generateToken(userResponse)
            call.respond(
                status = HttpStatusCode.OK,
                message = AuthResponse(
                    token = token
                )
            )
        } else {
            call.respond(
                status = HttpStatusCode.OK,
                message = AuthResponse(
                    message = "Invalid Credentials"
                )
            )
        }
    }

    post("register") {
        val userRequest = call.receive<UserRequest>()
        try {
            val user = userRequest.toRegisterUser()
            val userResponse = userService.createUser(user)
            call.respond(HttpStatusCode.Created, userResponse!!)
        } catch (e: IOException) {
            call.respond(HttpStatusCode.Conflict, "Something went wrong")
            return@post
        }
    }

    patch("updateuser") {
        val userRequest = call.receive<EditedUserRequest>()
        try {
            if (userRequest.requestedUsername == null) {
                userService.updateUser(userRequest)
                call.respond(HttpStatusCode.OK)
            } else {
                val token = userService.updateUserWithUsername(userRequest)
                call.respond(
                    HttpStatusCode.OK,
                    message = AuthResponse(
                    token = token,
                    message = null
                ))
            }
        } catch (e: Exception) {
            call.respond(message = hashMapOf("Error" to e.message))
        }
    }
}

private fun UserRequest.toRegisterUser(): User {
    return User(
        username = username,
        password = password,
        createdAt = System.currentTimeMillis()
    )
}

