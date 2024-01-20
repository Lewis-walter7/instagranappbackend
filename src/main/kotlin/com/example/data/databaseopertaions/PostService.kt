package com.example.data.databaseopertaions

import com.example.domain.dao.post.PostDao
import com.example.domain.models.Post
import com.example.domain.response.PostResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class PostService(
    private val postDao: PostDao
) {
    suspend fun createPost(post: Post): PostResponse{
        return postDao.createPost(post)
    }

    suspend fun getByPostsById(id: UUID): List<PostResponse> {
        return postDao.getPostsByUser(id)
    }

    suspend fun getSearchPosts(): List<PostResponse> {
        return withContext(Dispatchers.IO) {
            postDao.getSearchPosts()
        }
    }

}