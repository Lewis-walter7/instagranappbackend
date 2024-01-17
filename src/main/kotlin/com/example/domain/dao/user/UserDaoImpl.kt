package com.example.domain.dao.user

import com.example.domain.models.User
import com.example.domain.models.Users
import com.example.domain.response.UserResponse
import com.example.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.UUID

class UserDaoImpl: UserDao {

    private fun resultRowToUser(row: ResultRow) = UserResponse(
        id = row[Users.id].toString(),
        username = row[Users.username],
        password = row[Users.password],
        email = row[Users.email],
        profileImage = row[Users.profileImage],
        phoneNumber = row[Users.phoneNumber],
        bio = row[Users.bio],
        accountType = row[Users.accountType],
        createdAt = row[Users.createdAt]
    )

    override suspend fun createUser(user: User): UserResponse = dbQuery {
        val insertStatement = Users.insert {
            it[username] = user.username
            it[password] = user.password
            it[email] = user.email
            it[profileImage] = user.profileImage
            it[phoneNumber] = user.phoneNumber
            it[bio] = user.bio
            it[createdAt] = user.createdAt
        }
        insertStatement.resultedValues?.singleOrNull().let {
            resultRowToUser(it!!)
        }
    }



    override suspend fun findUserById(id: UUID): UserResponse? =  dbQuery {
            Users.select {
                Users.id eq id
            }
                .map {
                    resultRowToUser(it)
                }
                .singleOrNull()
        }

    override suspend fun findUserbyUsername(username: String): UserResponse? = dbQuery {
         Users.select {
            Users.username eq username
        }
            .map {
                resultRowToUser(it)
            }
             .singleOrNull()
    }
}