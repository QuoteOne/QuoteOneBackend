package com.app.service.valdator

import com.app.MissingAttributeWhileRelevantPolicyExists
import com.app.ValidationException
import com.app.repository.common.ValidationPolicy

class PolicyValidator(
    private val validationPolicies: Iterable<ValidationPolicy>,
    private val values: Map<String, Any>
) {

    fun validate(): ValidationException? {

        val failedValidations = mutableListOf<Validation>()
        
        for (policy in validationPolicies) {
            // Skip if the attribute doesn't exist in the values map
            val attributeValue = values[policy.attribute] ?: throw MissingAttributeWhileRelevantPolicyExists
            
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
