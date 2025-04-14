package com.app.service.valdator.impl

import com.app.service.valdator.*

class EqualValidator : BaseValidator(
    validationType = ValidationType.EQUAL,
    supportedTypes = ValueType.entries.toSet()
) {
    override fun validate(policy: ValidationPolicy, value: Any): Validation {
        validateType(policy, value)?.let { return it }

        return when (policy.valueType) {
            ValueType.NUMBER -> validateNumeric(policy, value)
            else -> validateString(policy, value)
        }
    }

    private fun validateNumeric(policy: ValidationPolicy, value: Any): Validation {
        val numericValue = when (value) {
            is Number -> value.toDouble()
            is String -> value.toDoubleOrNull()
            else -> null
        } ?: return Validation(
            failed = true,
            reason = "${policy.name}: Value must be a number for EQUAL validation"
        )

        val compareValue = policy.value.toDoubleOrNull() ?: return Validation(
            failed = true,
            reason = "${policy.name}: Invalid comparison value"
        )

        return when (numericValue == compareValue) {
            true -> Validation(failed = false, reason = null)
            false -> Validation(
                failed = true,
                reason = "${policy.name}: Value ($value) is not equal to ${policy.value}"
            )
        }
    }

    private fun validateString(policy: ValidationPolicy, value: Any): Validation {
        return when (value.toString() == policy.value) {
            true -> Validation(failed = false, reason = null)
            false -> Validation(
                failed = true,
                reason = "${policy.name}: Value ($value) is not equal to ${policy.value}"
            )
        }
    }
}

