package com.app.service.valdator

class ValidationException(
    validations: List<Validation>
): Exception("Validation failed: ${validations.joinToString { it.reason ?: "Unknown reason" }}")


class PolicyValidator(
    private val validationRequired: IValidationRequired
) {

    fun validate(): ValidationException? {
        val policies = validationRequired.validationPolicies
        val values = validationRequired.values

        val failedValidations = mutableListOf<Validation>()
        
        for (policy in policies) {
            // Skip if the attribute doesn't exist in the values map
            val attributeValue = values[policy.attribute] ?: continue
            
            // Create a validator instance for this policy's validation type
            val validator = ValidatorFactory.createValidator(policy.validationType)
            
            // Perform the validation
            val result = validator.validate(policy, attributeValue)
            
            // If validation failed, add it to the list
            if (result.failed) {
                failedValidations.add(result)
            }
        }
        
        // If there are any failed validations, create and return a ValidationException
        return if (failedValidations.isNotEmpty()) {
            ValidationException(failedValidations)
        } else {
            null
        }
    }
}
