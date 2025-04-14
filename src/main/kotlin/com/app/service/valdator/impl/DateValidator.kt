package com.app.service.valdator.impl

import com.app.service.valdator.Validation
import com.app.service.valdator.ValidationPolicy
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType
import java.time.LocalDate
import java.time.format.DateTimeParseException

class DateValidator : BaseValidator(
    validationType = ValidationType.FORMAT_VALIDATION,
    supportedTypes = setOf(ValueType.DATE)
) {
    
    override fun validate(policy: ValidationPolicy, value: Any?): Validation {
        validateType(policy, value)?.let { return it }
        
        when (value) {
            is String -> try {
                LocalDate.parse(value)
            } catch (e: DateTimeParseException) {
                return Validation(
                    failed = true,
                    reason = "${policy.name}: Invalid date format. Expected ISO format (yyyy-MM-dd)"
                )
            }
            else -> return Validation(
                failed = true,
                reason = "${policy.name}: Value must be a date"
            )
        }
        
        return Validation(failed = false, reason = null)
    }
} 