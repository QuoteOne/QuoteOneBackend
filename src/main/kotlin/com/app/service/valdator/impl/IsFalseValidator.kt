package com.app.service.valdator.impl

import com.app.repository.common.ValidationPolicy
import com.app.service.valdator.*


class IsFalseValidator : BaseValidator(
    validationType = ValidationType.IS_FALSE,
    supportedTypes = setOf(ValueType.BOOLEAN)
) {
    override fun validate(policy: ValidationPolicy, value: Any): Validation {
        validateType(policy, value)?.let { return it }

        val boolValue = when (value) {
            is Boolean -> value
            is String -> value.lowercase() == "true"
            else -> null
        } ?: return Validation(
            failed = true,
            reason = "${policy.name}: Value must be a boolean for IS_FALSE validation"
        )

        return if (!boolValue) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value is true when it should be false"
            )
        }
    }
}