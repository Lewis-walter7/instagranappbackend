package com.example.domain.routes

import com.example.domain.dao.user.UserDao
import com.example.domain.response.AuthenticationResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

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

fun Route.getUser(userDao: UserDao){
    authenticate {
        get("user") {
            try {
                val principal = call.authentication.principal<JWTPrincipal>()
                val id = principal?.getClaim("userid", String::class)

                val user = userDao.findUserById(UUID.fromString(id!!))
                if (user != null) {
                    call.respond(user)
                }
            } catch (e: Exception) {
                call.respond(hashMapOf("Error" to e.message))
            }
        }
    }

    get("userprofile") {
        val id = call.parameters["userId"]

        val user = userDao.findUserById(UUID.fromString(id))

        call.respond(user!!)
    }
}
