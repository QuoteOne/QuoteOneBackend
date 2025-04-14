package com.security.repository

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IUserRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String): User?
    fun existsUserByUsername(username: String): Boolean
    fun findByExternalId(externalId: String): User?
}