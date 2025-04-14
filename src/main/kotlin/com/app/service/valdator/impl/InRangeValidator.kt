package com.app.service.valdator.impl

import com.app.service.valdator.*

class InRangeValidator : BaseValidator(
    validationType = ValidationType.IN_RANGE,
    supportedTypes = setOf(ValueType.NUMBER)
) {
    override val validationType: ValidationType = ValidationType.IN_RANGE

    override fun validate(policy: ValidationPolicy, value: Any): Validation {
        validateType(policy, value)?.let { return it }


        // Expecting value in format "min,max"
        val rangeValues = policy.value.split(",").toList()
        if (rangeValues.size != 2 || rangeValues.any { it.toDoubleOrNull() == null }) {
            return Validation(
                failed = true,
                reason = "${policy.name}: Range value must be in format 'min,max' and convertable to number"
            )
        }


        val (min, max) = rangeValues.map { it.toDouble() }
        if (min > max) {
            return Validation(
                failed = true,
                reason = "${policy.name}: Invalid range values"
            )
        }

        val numericValue = when (value) {
            is Number -> value.toDouble()
            is String -> value.toDoubleOrNull()
            else -> null
        } ?: return Validation(
            failed = true,
            reason = "${policy.name}: Value must be a number for IN_RANGE validation"
        )

        val isInRange = (numericValue >= min) && (numericValue <= max)

        return if (isInRange) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value ($numericValue) is not in range $min to $max"
            )
        }
    }
}