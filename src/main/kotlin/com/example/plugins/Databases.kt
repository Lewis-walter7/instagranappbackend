package com.example.plugins

import com.example.domain.models.Comments
import com.example.domain.models.Messages
import com.example.domain.models.Posts
import com.example.domain.models.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(config: ApplicationConfig) {
        val driverClassName = config.property("ktor.database.driverClassName").getString()
        val jdbcURL = config.property("ktor.database.jdbcURL").getString()
        val maxPoolSize = config.property("ktor.database.maxPoolSize").getString()
        val autoCommit = config.property("ktor.database.autoCommit").getString()
        val username = config.property("ktor.database.username").getString()
        val password = config.property("ktor.database.password").getString()
        val defaultDatabase = config.property("ktor.database.database").getString()
        val connectionPool = createHikariDataSource(
            url = "$jdbcURL/$defaultDatabase?user=$username&password=$password",
            driver = driverClassName,
            maxPoolSize.toInt(),
            autoCommit.toBoolean()
        )
        val database = Database.connect(connectionPool)

        transaction(database) {
            SchemaUtils.create(Users)
            SchemaUtils.create(Posts)
            SchemaUtils.create(Comments)
            SchemaUtils.create(Messages)
        }
    }

    private fun createHikariDataSource(
        url: String,
        driver: String,
        maxPoolSize: Int,
        autoCommit: Boolean
    ) = HikariDataSource(HikariConfig().apply {
        driverClassName = driver
        jdbcUrl = url
        isAutoCommit = autoCommit
        maximumPoolSize = maxPoolSize
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    })

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) {
            block()
        }
}
