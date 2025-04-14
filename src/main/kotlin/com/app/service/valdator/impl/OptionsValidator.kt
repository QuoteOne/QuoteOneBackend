package com.app.service.valdator.impl

import com.app.service.valdator.Validation
import com.app.service.valdator.ValidationPolicy
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType

class OptionsValidator : BaseValidator(
    validationType = ValidationType.OPTIONS,
    supportedTypes = ValueType.entries.toSet()
) {
    
    override fun validate(policy: ValidationPolicy, value: Any): Validation {
        validateType(policy, value)?.let { return it }
        
        // Parse the options from the policy value (comma-separated)
        val options = policy.value.split(",").map { it.trim() }
        
        // Convert the value to string for comparison
        val stringValue = value.toString()
        
        return if (options.contains(stringValue)) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value '$stringValue' is not in the allowed options: ${options.joinToString()}"
            )
        }
    }
}