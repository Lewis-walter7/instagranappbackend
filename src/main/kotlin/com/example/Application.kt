package com.example

import com.example.data.databaseopertaions.PostService
import com.example.data.databaseopertaions.UserService
import com.example.domain.dao.user.PostDaoImpl
import com.example.domain.dao.user.UserDaoImpl
import com.example.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val userRepository = UserDaoImpl()
    val postRepostory = PostDaoImpl()
    val userService = UserService(userRepository)
    val postService = PostService(postRepostory)

    DatabaseFactory.init(environment.config)

    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting(
        userService = userService,
        userDao = userRepository,
        postService = postService
    )
}
