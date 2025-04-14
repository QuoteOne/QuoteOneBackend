package com.app.service.valdator.impl

import com.app.repository.models.ValidationPolicy
import com.app.service.valdator.Validation
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType

class MinLengthValidator : BaseValidator(
    validationType = ValidationType.MIN_LENGTH,
    supportedTypes = setOf(ValueType.STRING)
) {
    
    override fun validate(policy: ValidationPolicy, value: Any): Validation {
        validateType(policy, value)?.let { return it }

        val stringValue = value as String
        val minLength = policy.value.toIntOrNull() ?: return Validation(
            failed = true,
            reason = "${policy.name}: Invalid minimum length value"
        )
        
        return if (stringValue.length >= minLength) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value length (${stringValue.length}) is less than minimum required length ($minLength)"
            )
        }
    }
}

