package com.app.service.valdator

import com.app.service.valdator.impl.OptionsValidator
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

class OptionsValidatorTest {

    companion object {
        @JvmStatic
        fun optionsValidatorTestCases(): Stream<Arguments> = Stream.of(
            // String test cases
            Arguments.of("active", "active,inactive,pending", false, null),
            Arguments.of("inactive", "active,inactive,pending", false, null),
            Arguments.of("pending", "active,inactive,pending", false, null),
            Arguments.of("invalid", "active,inactive,pending", true, "test: Value 'invalid' is not in the allowed options: active, inactive, pending"),
            
            // Number test cases
            Arguments.of(1, "1,2,3", false, null),
            Arguments.of(2, "1,2,3", false, null),
            Arguments.of(3, "1,2,3", false, null),
            Arguments.of(4, "1,2,3", true, "test: Value '4' is not in the allowed options: 1, 2, 3"),
            
            // Boolean test cases
            Arguments.of(true, "true,false", false, null),
            Arguments.of(false, "true,false", false, null),
            Arguments.of("true", "true,false", false, null),
            Arguments.of("false", "true,false", false, null),
            
            // Edge cases
            Arguments.of("", "active,inactive,pending", true, "test: Value '' is not in the allowed options: active, inactive, pending"),
            Arguments.of(" ", "active,inactive,pending", true, "test: Value ' ' is not in the allowed options: active, inactive, pending"),
            Arguments.of("active", "", true, "test: Value 'active' is not in the allowed options: "),
            Arguments.of("active", "  ", true, "test: Value 'active' is not in the allowed options: ")
        )
    }

    @Test
    @DisplayName("OptionsValidator should have correct validation type")
    fun testValidationType() {
        val validator = OptionsValidator()
        assertEquals(ValidationType.OPTIONS, validator.validationType)
    }

    @Test
    @DisplayName("OptionsValidator should support all value types")
    fun testSupportedTypes() {
        val validator = OptionsValidator()
        ValueType.entries.forEach { type ->
            assertTrue(validator.isSupportType(type))
        }
    }

    @ParameterizedTest
    @MethodSource("optionsValidatorTestCases")
    @DisplayName("OptionsValidator should validate correctly")
    fun testOptionsValidator(value: Any, options: String, expectedFailed: Boolean, expectedReason: String?) {
        val validator = OptionsValidator()
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "test",
            attribute = "test",
            validationType = ValidationType.OPTIONS,
            valueType = ValueType.STRING,
            value = options
        )
        
        val result = validator.validate(policy, value)
        
        assertEquals(expectedFailed, result.failed)
        assertEquals(expectedReason, result.reason)
    }

    @Test
    @DisplayName("OptionsValidator should handle empty policy value")
    fun testEmptyPolicyValue() {
        val validator = OptionsValidator()
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "test",
            attribute = "test",
            validationType = ValidationType.OPTIONS,
            valueType = ValueType.STRING,
            value = ""
        )
        
        val result = validator.validate(policy, "test")
        
        assertTrue(result.failed)
        assertEquals("test: Value 'test' is not in the allowed options: ", result.reason)
    }
} 