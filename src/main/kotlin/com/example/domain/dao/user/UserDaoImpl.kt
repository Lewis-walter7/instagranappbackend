package com.example.domain.dao.user

import com.example.domain.models.User
import com.example.domain.models.Users
import com.example.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import java.util.UUID

class UserDaoImpl: UserDao {

    private fun resultRowToUser(row: ResultRow) = User(
        username = row[Users.username],
        password = row[Users.password],
        email = row[Users.email],
        phoneNumber = row[Users.phoneNumber],
        bio = row[Users.bio],
        accountType = row[Users.accountType],
        createdAt = row[Users.createdAt]
    )

    override suspend fun createUser(user: User): User = dbQuery{
        Users.insertAndGetId {
            it[username] = user.username
            it[password] = user.password
            it[email] = user.email
            it[phoneNumber] = user.phoneNumber
            it[bio] = user.bio
        }.let { userId ->
             findUserById(userId.value) ?: throw NoSuchElementException("User not found")
        }
    }



    override suspend fun findUserById(id: UUID): User? =  dbQuery {
            Users.select {
                Users.id eq id
            }
                .map {
                    resultRowToUser(it)
                }
                .singleOrNull()
        }


    override suspend fun findUserbyUsername(username: String): User? = dbQuery {
         Users.select {
            Users.username eq username
        }
            .map {
                resultRowToUser(it)
            }
             .singleOrNull()
    }
}