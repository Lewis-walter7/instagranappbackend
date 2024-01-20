package com.example.domain.dao.user

import com.example.domain.models.User
import com.example.domain.models.Users
import com.example.domain.requests.EditedUserRequest
import com.example.domain.response.UserResponse
import com.example.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
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
        createdAt = row[Users.createdAt],
        name = row[Users.name]
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
            it[name] = user.name
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

    override suspend fun editUser(user: EditedUserRequest): Unit = dbQuery {
        val existingUser = Users.select {
            Users.username eq user.requestedUsername.toString()
        }.map {
            resultRowToUser(it)
        }.singleOrNull()

        if (existingUser == null && user.requestedUsername != null) {
            Users.update({ Users.username eq user.username }) {
                it[profileImage] = user.profileImage
                it[bio] = user.bio
                it[name] = user.name
                it[username] = user.requestedUsername.toString()
            }
        } else{
            Users.update({ Users.username eq user.username }) {
                it[username] = user.username
                it[profileImage] = user.profileImage
                it[bio] = user.bio
                it[name] = user.name
            }
        }
    }

    override suspend fun getUsersByUser(username: String): List<UserResponse> = dbQuery {
        Users.select {
            Users.username like "%$username%"
        }
            .map {
                resultRowToUser(it)
            }
    }
}
