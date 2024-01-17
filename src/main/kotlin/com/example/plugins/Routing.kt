package com.example.plugins

import com.example.data.databaseopertaions.PostService
import com.example.data.databaseopertaions.UserService
import com.example.domain.dao.user.UserDao
import com.example.domain.routes.Authenticate
import com.example.domain.routes.PostRoutes
import com.example.domain.routes.UserRoutes
import com.example.domain.routes.getUsername
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userService: UserService,
    userDao: UserDao,
    postService: PostService
) {
    routing {
        UserRoutes(userService)
        Authenticate()
        getUsername(userDao)
        PostRoutes(postService)
    }
}
