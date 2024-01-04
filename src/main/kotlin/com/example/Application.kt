package com.example

import com.example.data.services.UserService
import com.example.domain.dao.user.UserDaoImpl
import com.example.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val userRepository = UserDaoImpl()
    val userService = UserService(userRepository)

    DatabaseFactory.init(environment.config)

    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting(userService = userService)
}
