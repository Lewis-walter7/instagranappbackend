package com.example.domain.routes

import com.example.data.services.JWTService
import com.example.data.services.UserService
import com.example.domain.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.IOException

fun Route.UserRoutes(userService: UserService) {
    post("/login") {
        val user = call.receive<User>()
        val userStatus = userService.loginUser(user.username, user.password)
        if (userStatus) {
            val token = JWTService.generateToken(user)
            call.respond(hashMapOf("token" to token))
        } else {
            call.respond("Invalid Credentials")
        }
    }

    post("/register") {
        val user = call.receive<User>()
        try {
            userService.createUser(user)
            call.respond(HttpStatusCode.OK)
        } catch (e: IOException) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
    }
}