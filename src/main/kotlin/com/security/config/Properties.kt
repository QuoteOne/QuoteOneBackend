package com.security.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "web")
data class WebProperties(
    @Value("\${unprotected-routes}") val unprotectedRoutes: List<String>,
    @Value("\${jwt-secret}") val jwtSecret: String,
    @Value("\${jwt-valid-seconds}") val jwtValidSeconds: Int,
)