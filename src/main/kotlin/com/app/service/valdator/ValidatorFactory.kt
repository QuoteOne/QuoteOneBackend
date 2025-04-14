package com.app.service.valdator

import com.app.service.valdator.impl.*

/**
 * Factory for creating validator instances based on validation type.
 */
object ValidatorFactory {
    
    /**
     * Creates and returns an appropriate validator instance based on the validation type.
     * 
     * @param validationType The type of validation to perform
     * @return An instance of a validator that implements the IValidator interface
     * @throws IllegalArgumentException if the validation type is not supported
     */
    fun createValidator(validationType: ValidationType): IValidator {
        return when (validationType) {
            // String validators
            ValidationType.CONTAINS -> ContainsValidator()
            ValidationType.STARTS_WITH -> StartsWithValidator()
            ValidationType.ENDS_WITH -> EndsWithValidator()
            ValidationType.MIN_LENGTH -> MinLengthValidator()
            ValidationType.MAX_LENGTH -> MaxLengthValidator()
            
            // Number validators
            ValidationType.GREATER_THAN -> GreaterThanValidator()
            ValidationType.GREATER_THAN_OR_EQUAL -> GreaterThanOrEqualValidator()
            ValidationType.LESS_THAN -> LessThanValidator()
            ValidationType.LESS_THAN_OR_EQUAL -> LessThanOrEqualValidator()
            ValidationType.NOT_EQUAL -> NotEqualValidator()
            ValidationType.IN_RANGE -> InRangeValidator()

            // String & Number Validators:
            ValidationType.EQUAL -> EqualValidator()
            
            // Boolean validators
            ValidationType.IS_TRUE -> IsTrueValidator()
            ValidationType.IS_FALSE -> IsFalseValidator()
            
            // General validators
            ValidationType.REQUIRED -> RequiredValidator()
        }
    }
} 