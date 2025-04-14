package com.app.service.valdator.impl

import com.app.repository.models.ValidationPolicy
import com.app.service.valdator.Validation
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType

class RequiredValidator : BaseValidator(
    validationType = ValidationType.REQUIRED,
    supportedTypes = ValueType.entries.toSet()
) {
    
    override fun validate(policy: ValidationPolicy, value: Any): Validation {
        validateType(policy, value)?.let { return it }
        
        return when (value) {
            is String -> if (value.isBlank()) {
                Validation(
                    failed = true,
                    reason = "${policy.name}: Value cannot be empty"
                )
            } else {
                Validation(failed = false, reason = null)
            }
            is Array<*> -> if (value.isEmpty()) {
                Validation(
                    failed = true,
                    reason = "${policy.name}: Array cannot be empty"
                )
            } else {
                Validation(failed = false, reason = null)
            }
            else -> Validation(failed = false, reason = null)
        }
    }
}