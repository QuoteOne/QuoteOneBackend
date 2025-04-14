package com.app.repository

import com.app.repository.models.ValidationPolicy
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IValidationPolicyRepository: JpaRepository<ValidationPolicy, UUID>