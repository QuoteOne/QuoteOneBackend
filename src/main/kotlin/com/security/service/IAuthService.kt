package com.security.service

import com.security.config.WebProperties
import com.security.repository.IUserRepository
import com.security.repository.User
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.jvm.optionals.getOrNull

interface IProvider {
    val name: String
}

interface IAuthService {
    val DefaultRoles: Iterable<String>
    fun assignRoles(userId: UUID, roles: Iterable<String>): User
    fun login(username: String, password: String): String
    fun userLogin(user: User): String
    fun writeTokenToCookie(response: HttpServletResponse, token: String)
    fun logout(response: HttpServletResponse)
    fun parseToken(token: String): User?
    fun isValidToken(token: String): Boolean
}

@Service
class AuthService(
    val webProperties: WebProperties,
    val userJwtService: UserJwtService,
    val userRepository: IUserRepository,
    val passwordEncoder: PasswordEncoder
) : IAuthService {

    override val DefaultRoles = setOf("ROLE_USER")

    @Transactional
    override fun assignRoles(userId: UUID, roles: Iterable<String>): User {
        val user = userRepository.findById(userId).getOrNull() ?: throw UserNotFound
        user.roles.clear()
        user.roles.addAll(roles.map { "ROLE_${it}" })
        val savedUser = userRepository.save(user)
        return savedUser
    }

    val JWT_KEY = "jwt"

    override fun login(username: String, password: String): String {
        val user = userRepository.findByUsername(username) ?: throw UserNotFound
        if (!passwordEncoder.matches(password, user.password)) throw PasswordMismatch

        return userJwtService.issueToken(user, webProperties.jwtValidSeconds)
    }

    override fun userLogin(user: User): String {
        return userJwtService.issueToken(user, webProperties.jwtValidSeconds)
    }

    override fun writeTokenToCookie(response: HttpServletResponse, token: String) {
        val cookie = Cookie(JWT_KEY, token)
        cookie.secure = true
        cookie.isHttpOnly = true
        cookie.path = "/"
        response.addCookie(cookie)
    }

    override fun logout(response: HttpServletResponse) {
        val cookie = Cookie(JWT_KEY, null)
        cookie.secure = true
        cookie.isHttpOnly = true
        cookie.path = "/"
        cookie.maxAge = 0
        response.addCookie(cookie)
    }

    override fun parseToken(token: String): User? {
        return userJwtService.parseToken(token)
    }

    override fun isValidToken(token: String): Boolean {
        return userJwtService.isValidToken(token)
    }
}