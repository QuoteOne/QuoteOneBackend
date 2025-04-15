package com.app.service.valdator

import com.app.repository.common.ValidationPolicy


// Don't add more types for simplicity.
enum class ValueType(
    val value: String
) {
    STRING("STRING"),
    NUMBER("NUMBER"),
    BOOLEAN("BOOLEAN"),
    DATE("DATE")
}



interface IValidationRequired {
    val validationPolicies: MutableSet<ValidationPolicy>
    val values: MutableMap<String, Any>

    fun validate() {
        val validator = PolicyValidator(this)
        val exception = validator.validate()
        if (exception != null) throw exception
    }

}