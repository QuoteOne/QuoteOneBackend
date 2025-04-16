package com.app.repository.material

import com.app.repository.common.ValidationPolicy
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType

class ValidationPolicyDefaults {

    companion object {
        val PolicyHeightShouldGreaterThanZero = ValidationPolicy(
            name = "長度不可為負值",
            attribute = "height",
            validationType = ValidationType.GREATER_THAN,
            valueType = ValueType.NUMBER,
            value = "0"
        )

        val PolicyWidthShouldGreaterThanZero = ValidationPolicy(
            name = "寬度不可為負值",
            attribute = "height",
            validationType = ValidationType.GREATER_THAN,
            valueType = ValueType.NUMBER,
            value = "0"
        )

        val PolicyDepthShouldGreaterThanZero = ValidationPolicy(
            name = "寬度不可為負值",
            attribute = "height",
            validationType = ValidationType.GREATER_THAN,
            valueType = ValueType.NUMBER,
            value = "0"
        )
    }
}