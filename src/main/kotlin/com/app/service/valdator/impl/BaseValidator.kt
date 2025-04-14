package com.app.service.valdator.impl

import com.app.service.valdator.IValidator
import com.app.service.valdator.Validation
import com.app.service.valdator.ValidationPolicy
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType

abstract class BaseValidator(
    override val validationType: ValidationType,
    private val supportedTypes: Set<ValueType>
) : IValidator {
    
    override fun supports(): Iterable<ValueType> = supportedTypes
    
    override fun isSupportType(type: ValueType): Boolean = supportedTypes.contains(type)
    
    protected fun validateType(policy: ValidationPolicy, value: Any?): Validation? {
        val valueType = when (value) {
            is String -> ValueType.STRING
            is Number -> ValueType.NUMBER
            is Boolean -> ValueType.BOOLEAN
            else -> null
        } ?: return Validation(
            failed = true,
            reason = "${policy.name}: Unsupported value type"
        )
        
        if (!isSupportType(valueType)) {
            return Validation(
                failed = true,
                reason = "${policy.name}: Value type $valueType is not supported for ${validationType.name} validation"
            )
        }
        
        return null
    }
} 