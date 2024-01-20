package com.example.domain.dao.user

import com.example.domain.dao.post.PostDao
import com.example.domain.models.Post
import com.example.domain.models.Posts
import com.example.domain.response.PostResponse
import com.example.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class PostDaoImpl: PostDao {

    private fun resultRowToPost(row: ResultRow) = PostResponse(
        id = row[Posts.id].toString(),
        userId = row[Posts.userId].toString(),
        showComments = row[Posts.showComments],
        hideLikes = row[Posts.hideLikes],
        likes = row[Posts.likes],
        postUrl = row[Posts.postUrl],
        createdAt = row[Posts.createdAt]
    )

    override suspend fun createPost(post: Post): PostResponse = dbQuery {
        val insertStatement = Posts.insert {
            it[postUrl] = post.postUrl
            it[createdAt] = post.createdAt
            it[userId] = post.userId
            it[likes] = post.likes
            it[caption] = post.caption
            it[hideLikes] = post.hideLikes
            it[showComments] = post.showComments
        }
        insertStatement.resultedValues?.singleOrNull().let {
            resultRowToPost(it!!)
        }
    }



    override suspend fun getSearchPosts(): List<PostResponse> =  dbQuery {
        Posts.selectAll()
            .map {
                resultRowToPost(it)
            }
    }

    override suspend fun getPostsByUser(id: UUID): List<PostResponse> {
        return transaction {
            Posts.select {
                Posts.userId eq id
            }
                .orderBy(Posts.createdAt, SortOrder.DESC)
                .map {
                resultRowToPost(it)
            }.toList()
        }
    }
}