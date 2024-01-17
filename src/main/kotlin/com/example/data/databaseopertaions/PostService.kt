package com.example.data.databaseopertaions

import com.example.domain.dao.post.PostDao
import com.example.domain.models.Post
import com.example.domain.response.PostResponse

class PostService(
    private val postDao: PostDao
) {
    suspend fun createPost(post: Post): PostResponse{
        return postDao.createPost(post)
    }
}