package com.app

import com.app.service.valdator.Validation

class ValidationException(
    validations: List<Validation>
): Exception("Validation failed: ${validations.joinToString { it.reason ?: "Unknown reason" }}")


class QuoteOneDomainError(message: String): Exception(message)

val SetValueNotAllowedMissingPolicy = QuoteOneDomainError("SetValueNotAllowed::MissingPolicies")
val MissingAttributeWhileRelevantPolicyExists = QuoteOneDomainError("MissingAttributeWhileRelevantPolicyExists")
val ProductNotFound = QuoteOneDomainError("ProductNotFound")
val KitProductNotFound = QuoteOneDomainError("KitProductNotFound")
val KitAlreadyExistsInProduct = QuoteOneDomainError("KitAlreadyExistsInProduct")
val KitNotFoundInProduct = QuoteOneDomainError("KitNotFoundInProduct")
val CategoryGroupNotSame = QuoteOneDomainError("CategoryGroupNotSame")
val CategoryNotFound = QuoteOneDomainError("CategoryNotFound")
val CategorySlugNotFound = QuoteOneDomainError("CategorySlugNotFound")