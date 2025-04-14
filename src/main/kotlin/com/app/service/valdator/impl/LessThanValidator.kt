package com.app.service.valdator.impl

import com.app.service.valdator.*

class LessThanValidator : IValidator {
    override val validationType: ValidationType = ValidationType.LESS_THAN
    override fun supports(): Iterable<ValueType> {
        TODO("Not yet implemented")
    }

    override fun isSupportType(type: ValueType): Boolean {
        TODO("Not yet implemented")
    }

    override fun validate(policy: ValidationPolicy, value: Any?): Validation {
        val numericValue = when (value) {
            is Number -> value.toDouble()
            is String -> value.toDoubleOrNull()
            else -> null
        } ?: return Validation(
            failed = true,
            reason = "${policy.name}: Value must be a number for LESS_THAN validation"
        )

        val compareValue = policy.value.toDoubleOrNull() ?: return Validation(
            failed = true,
            reason = "${policy.name}: Invalid comparison value"
        )

        return if (numericValue < compareValue) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value ($numericValue) is not less than $compareValue"
            )
        }
    }
}