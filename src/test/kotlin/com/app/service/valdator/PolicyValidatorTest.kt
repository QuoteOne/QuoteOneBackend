package com.app.service.valdator

import com.app.QuoteOneDomainError
import com.app.repository.common.ValidationPolicy
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class PolicyValidatorTest {


    @Test
    fun `should noy throw QuoteOneDomainError when attribute is present`() {
        // Given
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "Test Policy",
            attribute = "height",
            validationType = ValidationType.IN_RANGE,
            valueType = ValueType.STRING,
            value = "100-200"
        )

        val values = mapOf("height" to "150") // Missing "width" attribute

        // When & Then
        assertDoesNotThrow {
            PolicyValidator(arrayListOf(policy), values).validate()
        }
    }

    @Test
    fun `should throw QuoteOneDomainError when attribute is missing`() {
        // Given
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "Test Policy",
            attribute = "width",
            validationType = ValidationType.IN_RANGE,
            valueType = ValueType.STRING,
            value = "100-200"
        )

        val values = mapOf("height" to "150") // Missing "width" attribute

        // When & Then
        assertThrows<QuoteOneDomainError> {
            PolicyValidator(arrayListOf(policy), values).validate()
        }
    }
}