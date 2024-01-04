package com.example.plugins

import com.example.data.services.UserService
import com.example.domain.routes.UserRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userService: UserService
) {
    routing {
        UserRoutes(userService)
    }
}
