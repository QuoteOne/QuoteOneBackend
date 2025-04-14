package com.security.web.controller

import com.security.service.IAuthService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*


data class UserCredentials(
    val username: String,
    val password: String
)

@RestController
@RequestMapping("/api")
class AuthController(
    val authService: IAuthService
) {
    @GetMapping("/private/me")
    fun getCurrentUser(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<UserDetails> {
        return ResponseEntity(userDetails, HttpStatus.OK)
    }

    @GetMapping("/private/logout")
    fun logout(
        @AuthenticationPrincipal userDetails: UserDetails,
        response: HttpServletResponse
    ): ResponseEntity<Map<String, String>> {
        authService.logout(response)
        return ResponseEntity.ok(mapOf("message" to "OK"))
    }

    @PostMapping("/public/login")
    fun login(
        @RequestBody credentials: UserCredentials,
        response: HttpServletResponse
    ): ResponseEntity<Map<String, String>> {
        val token = authService.login(credentials.username, credentials.password)
        authService.writeTokenToCookie(response, token)
        return ResponseEntity.ok(mapOf("message" to "OK"))
    }

}