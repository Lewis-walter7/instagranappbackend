package com.example.domain.routes

import com.example.data.databaseopertaions.PostService
import com.example.domain.models.Post
import com.example.domain.requests.PostRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.IOException
import java.util.*

fun Route.PostRoutes(
    postService: PostService
){
    post("upload") {
        val request = call.receive<PostRequest>()

        try {
            val post = request.toPost()
            val response = postService.createPost(post)
            call.respond(
                HttpStatusCode.OK,
                message = response
            )
        } catch (e: IOException) {
            call.respond(
                message = "Internal server error"
            )
        }
    }
}

fun PostRequest.toPost(): Post {
    return Post(
        showComments = showComments,
        hideLikes = hideLikes,
        postUrl = postUrl,
        userId = UUID.fromString(userId),
        createdAt = System.currentTimeMillis(),
        caption = caption
    )
}