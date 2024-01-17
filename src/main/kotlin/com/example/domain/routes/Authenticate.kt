package com.example.domain.routes

import com.example.domain.dao.user.UserDao
import com.example.domain.response.AuthenticationResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.Authenticate() {
    authenticate {
        get("authenticate") {
            call.respond(
                status =HttpStatusCode.OK,
                message = AuthenticationResponse(
                    isAuthenticated = true
                )
            )
        }
    }
}

fun Route.getUsername(userDao: UserDao){
    authenticate {
        get("user") {
            try {
                val principal = call.authentication.principal<JWTPrincipal>()
                val username = principal?.getClaim("username", String::class)

                val user = userDao.findUserbyUsername(username!!)
                if (user != null) {
                    call.respond(user)
                }
            } catch (e: Exception) {
                call.respond(e)
            }
        }
    }
}
