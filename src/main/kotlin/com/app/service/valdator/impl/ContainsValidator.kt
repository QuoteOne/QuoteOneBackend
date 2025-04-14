package com.app.service.valdator.impl

import com.app.service.valdator.Validation
import com.app.service.valdator.ValidationPolicy
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType

class ContainsValidator : BaseValidator(
    validationType = ValidationType.CONTAINS,
    supportedTypes = setOf(ValueType.STRING)
) {
    
    override fun validate(policy: ValidationPolicy, value: Any?): Validation {
        validateType(policy, value)?.let { return it }
        
        val stringValue = value as String
        val targetValue = policy.value
        
        return if (stringValue.contains(targetValue)) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value does not contain '$targetValue'"
            )
        }
    }
}

