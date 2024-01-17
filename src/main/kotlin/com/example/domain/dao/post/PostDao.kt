package com.example.domain.dao.post

import com.example.domain.models.Post
import com.example.domain.response.PostResponse

interface PostDao {
    suspend fun createPost(post:Post): PostResponse
    suspend fun getPosts(id: String): List<PostResponse>
    suspend fun getPostsByUser(): List<PostResponse>
}
