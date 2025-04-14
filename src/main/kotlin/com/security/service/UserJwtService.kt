package com.security.service

import com.security.config.WebProperties
import com.security.repository.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserJwtService(
    @Value("\${spring.application.name}")
    val issuer: String,
    val webProperties: WebProperties,
    val defaultJwtService: DefaultJwtService,
) : IJwtService<User> {
    override lateinit var secret: ByteArray

    @PostConstruct
    fun postConstruct() {
        secret = Base64.getDecoder().decode(webProperties.jwtSecret)
    }

    override fun issueToken(
        claims: User,
        expireAtNumberOfSeconds: Int
    ): String {
        val signedKey = Keys.hmacShaKeyFor(secret)
        val userClaims = Jwts.builder()
            .issuer(issuer)
            .subject(claims.id.toString())
            .claim("roles", claims.roles)
            .claim("username", claims.username)
            .claim("provider", claims.provider)
            .claim("externalId", claims.externalId ?: "")
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expireAtNumberOfSeconds.toLong() * 1000))
            .signWith(signedKey)
            .compact()
        return userClaims
    }

    override fun parseToken(token: String): User? {
        val claims = defaultJwtService.parseToken(token) ?: return null
        return User(
            id = UUID.fromString(claims["sub"] as String),
            username = claims["username"] as String,
            roles = (claims["roles"] as List<*>).filterIsInstance<String>().toMutableSet(),
            password = "(sensitive)",
            provider = claims["provider"] as String,
            externalId = claims["externalId"] as String,
        )
    }

    override fun isValidToken(token: String): Boolean {
        return defaultJwtService.isValidToken(token)
    }
}