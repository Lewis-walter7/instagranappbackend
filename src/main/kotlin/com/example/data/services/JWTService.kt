package com.example.data.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.domain.models.User

object JWTService {
    const val issuer = "instagramServer"
    val jwt_secret = System.getenv("JWT_SECRET")
    val algorithm = Algorithm.HMAC256(jwt_secret)

    fun generateToken(user: User): String {
        return JWT.create()
            .withSubject("InstagramAuthentication")
            .withIssuer(issuer)
            .withClaim("username", user.username)
            .sign(algorithm)
    }
}