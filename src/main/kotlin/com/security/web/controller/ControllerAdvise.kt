package com.security.web.controller


import com.security.service.SecurityDomainError
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ErrorResponse(
    val timestamp: Long,
    val status: HttpStatus,
    val error: String?,
    val path: String,
)


@RestControllerAdvice
class ControllerAdvise {

    @ExceptionHandler(SecurityDomainError::class)
    fun securityDomainExceptionHandler(
        exception: SecurityDomainError,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            System.currentTimeMillis(),
            HttpStatus.UNAUTHORIZED,
            exception.message,
            request.requestURI
        )
        return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(Exception::class)
    fun globalExceptionHandler(
        exception: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val errorResponse = ErrorResponse(
            System.currentTimeMillis(),
            HttpStatus.INTERNAL_SERVER_ERROR,
            exception.message,
            request.requestURI

        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)

    }

}