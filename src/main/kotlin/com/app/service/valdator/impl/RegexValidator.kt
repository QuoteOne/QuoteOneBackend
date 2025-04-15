package com.app.service.valdator.impl

import com.app.repository.models.common.ValidationPolicy
import com.app.service.valdator.Validation
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType

class RegexValidator : BaseValidator(
    validationType = ValidationType.REGEX,
    supportedTypes = setOf(ValueType.STRING)
) {
    
    override fun validate(policy: ValidationPolicy, value: Any): Validation {
        validateType(policy, value)?.let { return it }
        
        val stringValue = value as String
        val regex = try {
            policy.value.toRegex()
        } catch (e: Exception) {
            return Validation(
                failed = true,
                reason = "${policy.name}: Invalid regex pattern"
            )
        }
        
        return if (regex.matches(stringValue)) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value does not match required pattern"
            )
        }
    }
} 