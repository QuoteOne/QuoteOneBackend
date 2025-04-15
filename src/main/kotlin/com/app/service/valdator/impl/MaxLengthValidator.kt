package com.app.service.valdator.impl

import com.app.repository.common.ValidationPolicy
import com.app.service.valdator.Validation
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType

class MaxLengthValidator : BaseValidator(
    validationType = ValidationType.MAX_LENGTH,
    supportedTypes = setOf(ValueType.STRING)
) {
    
    override fun validate(policy: ValidationPolicy, value: Any): Validation {
        validateType(policy, value)?.let { return it }

        val stringValue = value as String
        val maxLength = policy.value.toIntOrNull() ?: return Validation(
            failed = true,
            reason = "${policy.name}: Invalid maximum length value"
        )
        
        return if (stringValue.length <= maxLength) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value length (${stringValue.length}) exceeds maximum allowed length ($maxLength)"
            )
        }
    }
}