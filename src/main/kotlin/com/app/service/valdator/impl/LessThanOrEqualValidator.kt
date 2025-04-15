package com.app.service.valdator.impl

import com.app.repository.common.ValidationPolicy
import com.app.service.valdator.*


class LessThanOrEqualValidator : BaseValidator(
    validationType = ValidationType.LESS_THAN_OR_EQUAL,
    supportedTypes = setOf(ValueType.NUMBER)
) {
    override fun validate(policy: ValidationPolicy, value: Any): Validation {
        validateType(policy, value)?.let { return it }

        val numericValue = when (value) {
            is Number -> value.toDouble()
            is String -> value.toDoubleOrNull()
            else -> null
        } ?: return Validation(
            failed = true,
            reason = "${policy.name}: Value must be a number for LESS_THAN_OR_EQUAL validation"
        )

        val compareValue = policy.value.toDoubleOrNull() ?: return Validation(
            failed = true,
            reason = "${policy.name}: Invalid comparison value"
        )

        return if (numericValue <= compareValue) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value ($numericValue) is not less than or equal to $compareValue"
            )
        }
    }
}

