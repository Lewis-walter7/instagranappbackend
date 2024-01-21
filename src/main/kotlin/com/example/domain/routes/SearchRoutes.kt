package com.example.domain.routes

import com.example.data.databaseopertaions.UserService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.SearchRoutes(
    userServce: UserService
) {
    get("getUserByUsername") {
        val username = call.parameters["username"]!!
        val users = userServce.getUsersByUser(username)
        call.respond(
            users
        )
    }
}