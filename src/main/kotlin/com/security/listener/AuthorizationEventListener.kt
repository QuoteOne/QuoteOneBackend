package com.security.listener

import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.security.authorization.event.AuthorizationDeniedEvent
import org.springframework.stereotype.Component


@Component
class AuthorizationEventListener {
    val logger = LoggerFactory.getLogger(AuthorizationEventListener::class.java)

    @EventListener
    fun onFailure(failure: AuthorizationDeniedEvent<Any>) {
        logger.warn("Authorization denied: ${failure.authentication.get()}, denied resulting from ${failure.authorizationResult}")
    }
}