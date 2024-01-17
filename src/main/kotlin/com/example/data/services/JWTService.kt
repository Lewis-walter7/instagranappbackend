package com.example.data.services

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.domain.response.UserResponse
import java.util.*

object JWTService {
    private const val issuer = "instagramServer"
    private val jwt_secret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC256(jwt_secret)
    val jwtAudience = "jwt-audience"

    fun generateToken(user: UserResponse): String {
        return JWT.create()
            .withSubject("InstagramAuthentication")
            .withAudience(jwtAudience)
            .withIssuer(issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + 31536000000))
            .withClaim("userid", user.id)
            .withClaim("username", user.username)
            .sign(algorithm)
    }
}