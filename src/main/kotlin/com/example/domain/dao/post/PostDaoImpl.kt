package com.example.domain.dao.post

import com.example.domain.models.Post

class PostDaoImpl: PostDao {
    override suspend fun createPost(): Post {
        TODO("Not yet implemented")
    }

    override suspend fun getPosts(id: String): List<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostsByUser(): List<Post> {
        TODO("Not yet implemented")
    }
}