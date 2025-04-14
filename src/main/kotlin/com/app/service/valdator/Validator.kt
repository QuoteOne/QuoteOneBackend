package com.app.service.valdator

enum class ValidationType {
    // String
    CONTAINS,
    STARTS_WITH,
    ENDS_WITH,
    MIN_LENGTH,
    MAX_LENGTH,
    FORMAT_VALIDATION,
    REGEX,

    // Number
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL,
    LESS_THAN,
    LESS_THAN_OR_EQUAL,
    EQUAL,
    NOT_EQUAL,
    IN_RANGE, // useful for number or date types

    // Boolean
    IS_TRUE,
    IS_FALSE,

    // General / Cross-type
    REQUIRED
}


data class Validation(
    val failed: Boolean,
    val reason: String?
)


interface IValidator {
    val validationType: ValidationType
    fun supports(): Iterable<ValueType>
    fun isSupportType(type: ValueType): Boolean
    fun validate(policy: ValidationPolicy, value: Any): Validation
}