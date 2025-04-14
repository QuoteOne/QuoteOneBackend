package com.security.service


class SecurityDomainError(override val message: String?) : RuntimeException(message)


val UserNotFound = SecurityDomainError("UserNotFound")
val PasswordMismatch = SecurityDomainError("PasswordMismatch")