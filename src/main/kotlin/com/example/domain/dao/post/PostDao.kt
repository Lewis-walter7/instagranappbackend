package com.example.domain.dao.post

import com.example.domain.models.Post

interface PostDao {
    suspend fun createPost(): Post
    suspend fun getPosts(id: String): List<Post>
    suspend fun getPostsByUser(): List<Post>
}
