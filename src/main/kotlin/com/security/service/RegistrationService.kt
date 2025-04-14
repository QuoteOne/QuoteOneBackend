package com.security.service

import com.security.repository.IUserRepository
import com.security.repository.User
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

class UserRegistrationEvent(val user: User) : ApplicationEvent(user)


interface IRegistrationService {
    fun registerUser(username: String, password: String, roles: Iterable<String>): User
    fun registerOauthUser(
        username: String,
        email: String,
        profileImageUrl: String,
        roles: Iterable<String>,
        provider: String,
        externalId: String?,
    ): User
}

@Service
class RegistrationService(
    private val userRepository: IUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val applicationEventPublisher: ApplicationEventPublisher,
) : IRegistrationService {

    val logger = LoggerFactory.getLogger(RegistrationService::class.java)

    @Transactional
    override fun registerUser(username: String, password: String, roles: Iterable<String>): User {
        val optionalUser = userRepository.findByUsername(username)
        if (optionalUser != null) return optionalUser

        val hashedPassword = passwordEncoder.encode(password)
        val user = User(
            name = username,
            password = hashedPassword,
            roles = roles.toSet(),
            provider = User.DefaultPlatform,
            externalId = null
        )
        val savedUser = userRepository.save(user)
        return savedUser
    }

    @Transactional
    override fun registerOauthUser(
        username: String,
        email: String,
        profileImageUrl: String,
        roles: Iterable<String>,
        provider: String,
        externalId: String?,
    ): User {
        var eventUser: User? = null

        try {
            val optionalUser = userRepository.findByExternalId(externalId ?: "")
            if (optionalUser != null) {
                eventUser = optionalUser
                return optionalUser
            }
            val user = User(name = username, password = "", roles.toSet(), provider, externalId)
            val savedUser = userRepository.save(user)
            eventUser = savedUser
            return savedUser
        } finally {
            logger.info("User registration event published...")
            eventUser?.run {
                applicationEventPublisher.publishEvent(UserRegistrationEvent(eventUser))
            }
        }

    }
}