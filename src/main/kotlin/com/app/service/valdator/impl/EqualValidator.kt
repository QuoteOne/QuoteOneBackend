package com.app.service.valdator.impl

import com.app.service.valdator.*

class EqualValidator : BaseValidator(
    validationType = ValidationType.NOT_EQUAL,
    supportedTypes =  ValueType.entries.toSet()
) {
    override val validationType: ValidationType = ValidationType.EQUAL

    override fun validate(policy: ValidationPolicy, value: Any?): Validation {
        return if (value == policy.value) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "${policy.name}: Value ($value) is not equal to ${policy.value}"
            )
        }
    }
}

