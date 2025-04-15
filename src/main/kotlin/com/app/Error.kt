package com.app

import com.app.service.valdator.Validation

class ValidationException(
    validations: List<Validation>
): Exception("Validation failed: ${validations.joinToString { it.reason ?: "Unknown reason" }}")


class QuoteOneDomainError(message: String): Exception(message)

val SetValueNotAllowedMissingPolicy = QuoteOneDomainError("SetValueNotAllowed::MissingPolicies")
val MissingAttributeWhileRelevantPolicyExists = QuoteOneDomainError("MissingAttributeWhileRelevantPolicyExists")