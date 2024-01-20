package com.example.domain.dao.post

import com.example.domain.models.Post
import com.example.domain.response.PostResponse
import java.util.UUID

interface PostDao {
    suspend fun createPost(post:Post): PostResponse
    suspend fun getSearchPosts(): List<PostResponse>
    suspend fun getPostsByUser(id: UUID): List<PostResponse>
}
