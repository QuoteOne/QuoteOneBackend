package com.app.service.valdator

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID


// Don't add more types for simplicity.
enum class ValueType(
    val value: String
) {
    STRING("STRING"),
    NUMBER("NUMBER"),
    BOOLEAN("BOOLEAN"),
    DATE("DATE")
}

@Entity
class ValidationPolicy(
    @Id
    val id: UUID,
    val name: String,
    val attribute: String,
    val validationType: ValidationType,
    val valueType: ValueType,
    val value: String
)

interface IValidationRequired {
    val validationPolicies: Iterable<ValidationPolicy>
    val values: Map<String, Any>

    fun validate() {
        for (policy in validationPolicies) {
            val validators = PolicyValidator(this)
            val exception = validators.validate()
            if (exception != null) throw exception
        }

    }

}