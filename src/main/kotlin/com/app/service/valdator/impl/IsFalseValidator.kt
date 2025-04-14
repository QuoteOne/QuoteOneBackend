package com.app.service.valdator.impl

import com.app.service.valdator.*


class IsFalseValidator : IValidator {
    override val validationType: ValidationType = ValidationType.IS_FALSE
    override fun supports(): Iterable<ValueType> {
        TODO("Not yet implemented")
    }

    override fun isSupportType(type: ValueType): Boolean {
        TODO("Not yet implemented")
    }

    override fun validate(policy: ValidationPolicy, value: Any?): Validation {
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