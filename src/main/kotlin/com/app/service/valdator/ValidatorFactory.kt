package com.app.service.valdator

import com.app.service.valdator.impl.*

/**
 * Factory for creating validator instances based on validation type.
 */
object ValidatorFactory {
    
    private val validators = mapOf(
        ValidationType.REQUIRED to RequiredValidator(),
        ValidationType.FORMAT_VALIDATION to FormatValidator(),
        ValidationType.EQUAL to EqualValidator(),
        ValidationType.NOT_EQUAL to NotEqualValidator(),
        ValidationType.GREATER_THAN to GreaterThanValidator(),
        ValidationType.LESS_THAN to LessThanValidator(),
        ValidationType.GREATER_THAN_OR_EQUAL to GreaterThanOrEqualValidator(),
        ValidationType.LESS_THAN_OR_EQUAL to LessThanOrEqualValidator(),
        ValidationType.IN_RANGE to InRangeValidator(),
        ValidationType.IS_TRUE to IsTrueValidator(),
        ValidationType.IS_FALSE to IsFalseValidator(),
        ValidationType.CONTAINS to ContainsValidator(),
        ValidationType.STARTS_WITH to StartsWithValidator(),
        ValidationType.ENDS_WITH to EndsWithValidator(),
        ValidationType.MIN_LENGTH to MinLengthValidator(),
        ValidationType.MAX_LENGTH to MaxLengthValidator(),
        ValidationType.REGEX to RegexValidator(),
        ValidationType.DATE to DateValidator()
    )

    /**
     * Creates and returns an appropriate validator instance based on the validation type.
     * 
     * @param validationType The type of validation to perform
     * @return An instance of a validator that implements the IValidator interface
     * @throws IllegalArgumentException if the validation type is not supported
     */
    fun createValidator(validationType: ValidationType): IValidator {
        return validators[validationType] ?: throw IllegalArgumentException("No validator found for type: $validationType")
    }
} 