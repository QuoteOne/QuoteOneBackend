package com.app.service.valdator.impl

import com.app.repository.models.ValidationPolicy
import com.app.service.valdator.Validation
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType

class GreaterThanValidator : BaseValidator(
    validationType = ValidationType.GREATER_THAN,
    supportedTypes = setOf(ValueType.NUMBER)
) {
    
    override fun validate(policy: ValidationPolicy, value: Any): Validation {
        validateType(policy, value)?.let { return it }
        
        val numericValue = (value as Number).toDouble()
        val compareValue = policy.value.toDoubleOrNull() ?: return Validation(
            failed = true,
            reason = "${policy.name}: Invalid comparison value"
        )
        
        return if (numericValue > compareValue) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value ($numericValue) is not greater than $compareValue"
            )
        }
    }
}

