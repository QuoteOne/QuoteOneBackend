package com.app.service.valdator.impl

import com.app.service.valdator.Validation
import com.app.service.valdator.ValidationPolicy
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType

class IsTrueValidator : BaseValidator(
    validationType = ValidationType.IS_TRUE,
    supportedTypes = setOf(ValueType.BOOLEAN)
) {
    
    override fun validate(policy: ValidationPolicy, value: Any?): Validation {
        validateType(policy, value)?.let { return it }
        
        val boolValue = value as Boolean
        
        return if (boolValue) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value is false when it should be true"
            )
        }
    }
}