package com.app.service.valdator.impl

import com.app.service.valdator.*


class NotEqualValidator : BaseValidator(
    validationType = ValidationType.NOT_EQUAL,
    ValueType.entries.toSet()
) {
    override fun validate(policy: ValidationPolicy, value: Any?): Validation {
        return if (value.toString() != policy.value) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value ($value) is equal to ${policy.value} when it should not be"
            )
        }
    }
}