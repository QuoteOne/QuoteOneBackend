package com.app.service.valdator

import com.app.service.valdator.impl.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import java.util.stream.Stream

class ValidatorTest {

    // =============================================
    // ValidatorFactory Tests
    // =============================================
    @Test
    @DisplayName("ValidatorFactory should create correct validator instances")
    fun testValidatorFactory() {
        // Test all validation types
        ValidationType.entries.forEach { type ->
            val validator = ValidatorFactory.createValidator(type)
            assertEquals(type, validator.validationType)
        }

        // Test invalid validation type
        assertThrows(IllegalArgumentException::class.java) {
            ValidatorFactory.createValidator(ValidationType.valueOf("INVALID_TYPE"))
        }
    }

    // =============================================
    // RequiredValidator Tests
    // =============================================
    @ParameterizedTest
    @MethodSource("requiredValidatorTestCases")
    @DisplayName("RequiredValidator should validate correctly")
    fun testRequiredValidator(value: Any?, expectedFailed: Boolean, expectedReason: String?) {
        val validator = RequiredValidator()
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "test",
            attribute = "test",
            validationType = ValidationType.REQUIRED,
            valueType = ValueType.STRING,
            value = ""
        )
        
        val result = if (value == null) {
            validator.validate(policy, "")
        } else {
            validator.validate(policy, value)
        }
        
        assertEquals(expectedFailed, result.failed)
        assertEquals(expectedReason, result.reason)
    }

    // =============================================
    // FormatValidator Tests
    // =============================================
    @ParameterizedTest
    @MethodSource("formatValidatorTestCases")
    @DisplayName("FormatValidator should validate correctly")
    fun testFormatValidator(value: Any?, format: String?, expectedFailed: Boolean, expectedReason: String?) {
        val validator = FormatValidator()
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "test",
            attribute = "test",
            validationType = ValidationType.FORMAT_VALIDATION,
            valueType = ValueType.STRING,
            value = format ?: ""
        )
        
        val result = if (value == null) {
            validator.validate(policy, "")
        } else {
            validator.validate(policy, value)
        }
        
        assertEquals(expectedFailed, result.failed)
        assertEquals(expectedReason, result.reason)
    }

    // =============================================
    // Numeric Validators Tests
    // =============================================
    @ParameterizedTest
    @MethodSource("greaterThanValidatorTestCases")
    @DisplayName("GreaterThanValidator should validate correctly")
    fun testGreaterThanValidator(value: Any?, expectedFailed: Boolean, expectedReason: String?) {
        val validator = GreaterThanValidator()
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "test",
            attribute = "test",
            validationType = ValidationType.GREATER_THAN,
            valueType = ValueType.NUMBER,
            value = "5"
        )
        
        val result = if (value == null) {
            validator.validate(policy, 0)
        } else {
            validator.validate(policy, value)
        }
        
        assertEquals(expectedFailed, result.failed)
        assertEquals(expectedReason, result.reason)
    }

    @ParameterizedTest
    @MethodSource("lessThanValidatorTestCases")
    @DisplayName("LessThanValidator should validate correctly")
    fun testLessThanValidator(value: Any?, expectedFailed: Boolean, expectedReason: String?) {
        val validator = LessThanValidator()
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "test",
            attribute = "test",
            validationType = ValidationType.LESS_THAN,
            valueType = ValueType.NUMBER,
            value = "5"
        )
        
        val result = if (value == null) {
            validator.validate(policy, 0)
        } else {
            validator.validate(policy, value)
        }
        
        assertEquals(expectedFailed, result.failed)
        assertEquals(expectedReason, result.reason)
    }

    @ParameterizedTest
    @MethodSource("equalValidatorTestCases")
    @DisplayName("EqualValidator should validate correctly")
    fun testEqualValidator(value: Any?, expectedFailed: Boolean, expectedReason: String?) {
        val validator = EqualValidator()
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "test",
            attribute = "test",
            validationType = ValidationType.EQUAL,
            valueType = ValueType.NUMBER,
            value = "5"
        )
        
        val result = if (value == null) {
            validator.validate(policy, 0)
        } else {
            validator.validate(policy, value)
        }
        
        assertEquals(expectedFailed, result.failed)
        assertEquals(expectedReason, result.reason)
    }

    @ParameterizedTest
    @MethodSource("notEqualValidatorTestCases")
    @DisplayName("NotEqualValidator should validate correctly")
    fun testNotEqualValidator(value: Any?, expectedFailed: Boolean, expectedReason: String?) {
        val validator = NotEqualValidator()
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "test",
            attribute = "test",
            validationType = ValidationType.NOT_EQUAL,
            valueType = ValueType.NUMBER,
            value = "5"
        )
        
        val result = if (value == null) {
            validator.validate(policy, 0)
        } else {
            validator.validate(policy, value)
        }
        
        assertEquals(expectedFailed, result.failed)
        assertEquals(expectedReason, result.reason)
    }

    // =============================================
    // String Validators Tests
    // =============================================
    @ParameterizedTest
    @MethodSource("contentValidatorTestCases")
    @DisplayName("Content validators should validate correctly")
    fun testContentValidators(
        validator: IValidator,
        value: Any?,
        expectedFailed: Boolean,
        expectedReason: String?,
        policyValue: String
    ) {
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "test",
            attribute = "test",
            validationType = validator.validationType,
            valueType = ValueType.STRING,
            value = policyValue
        )
        
        val result = if (value == null) {
            validator.validate(policy, "")
        } else {
            validator.validate(policy, value)
        }
        
        assertEquals(expectedFailed, result.failed)
        assertEquals(expectedReason, result.reason)
    }

    @ParameterizedTest
    @MethodSource("lengthValidatorTestCases")
    @DisplayName("Length validators should validate correctly")
    fun testLengthValidators(
        validator: IValidator,
        value: Any?,
        expectedFailed: Boolean,
        expectedReason: String?,
        policyValue: String
    ) {
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "test",
            attribute = "test",
            validationType = validator.validationType,
            valueType = ValueType.STRING,
            value = policyValue
        )
        
        val result = if (value == null) {
            validator.validate(policy, "")
        } else {
            validator.validate(policy, value)
        }
        
        assertEquals(expectedFailed, result.failed)
        assertEquals(expectedReason, result.reason)
    }

    // =============================================
    // Boolean Validators Tests
    // =============================================
    @ParameterizedTest
    @MethodSource("booleanValidatorTestCases")
    @DisplayName("Boolean validators should validate correctly")
    fun testBooleanValidators(
        validator: IValidator,
        value: Any?,
        expectedFailed: Boolean,
        expectedReason: String?
    ) {
        val policy = ValidationPolicy(
            id = UUID.randomUUID(),
            name = "test",
            attribute = "test",
            validationType = validator.validationType,
            valueType = ValueType.BOOLEAN,
            value = ""
        )
        
        val result = if (value == null) {
            validator.validate(policy, false)
        } else {
            validator.validate(policy, value)
        }
        
        assertEquals(expectedFailed, result.failed)
        assertEquals(expectedReason, result.reason)
    }

    companion object {
        // =============================================
        // Test Cases
        // =============================================

        // Required Validator Test Cases
        @JvmStatic
        fun requiredValidatorTestCases(): Stream<Arguments> = Stream.of(
            Arguments.of("", true, "test: Value cannot be empty"),
            Arguments.of(" ", true, "test: Value cannot be empty"),
            Arguments.of("test", false, null),
            Arguments.of(123, false, null),
            Arguments.of(true, false, null)
        )

        // Format Validator Test Cases
        @JvmStatic
        fun formatValidatorTestCases(): Stream<Arguments> = Stream.of(
            Arguments.of("test@example.com", "email", false, null),
            Arguments.of("invalid-email", "email", true, "test: Invalid email format"),
            Arguments.of("https://example.com", "url", false, null),
            Arguments.of("not-a-url", "url", true, "test: Invalid URL format"),
            Arguments.of("2023-01-01", "date", false, null),
            Arguments.of("invalid-date", "date", true, "test: Invalid date format (YYYY-MM-DD)"),
            Arguments.of(123, "email", true, "test: Value type NUMBER is not supported for FORMAT_VALIDATION validation")
        )

        // Numeric Validator Test Cases
        @JvmStatic
        fun greaterThanValidatorTestCases(): Stream<Arguments> = Stream.of(
            Arguments.of(6.0, false, null),
            Arguments.of(5.0, true, "test: Value (5.0) is not greater than 5.0"),
            Arguments.of(4.0, true, "test: Value (4.0) is not greater than 5.0")
        )

        @JvmStatic
        fun lessThanValidatorTestCases(): Stream<Arguments> = Stream.of(
            Arguments.of(4.0, false, null),
            Arguments.of(5.0, true, "test: Value (5.0) is not less than 5.0"),
            Arguments.of(6.0, true, "test: Value (6.0) is not less than 5.0")
        )

        @JvmStatic
        fun equalValidatorTestCases(): Stream<Arguments> = Stream.of(
            Arguments.of(5.0, false, null),
            Arguments.of(6.0, true, "test: Value (6.0) is not equal to 5"),
            Arguments.of("not-a-number", true, "test: Value must be a number for EQUAL validation")
        )

        @JvmStatic
        fun notEqualValidatorTestCases(): Stream<Arguments> = Stream.of(
            Arguments.of(6.0, false, null),
            Arguments.of(5.0, true, "test: Value (5.0) is equal to 5 when it should not be"),
            Arguments.of("not-a-number", true, "test: Value must be a number for NOT_EQUAL validation")
        )

        // String Content Validator Test Cases
        @JvmStatic
        fun contentValidatorTestCases(): Stream<Arguments> = Stream.of(
            // ContainsValidator
            Arguments.of(ContainsValidator(), "test123", false, null, "test"),
            Arguments.of(ContainsValidator(), "123", true, "test: Value does not contain 'test'", "test"),
            
            // StartsWithValidator
            Arguments.of(StartsWithValidator(), "test123", false, null, "test"),
            Arguments.of(StartsWithValidator(), "123test", true, "test: Value does not start with 'test'", "test"),
            
            // EndsWithValidator
            Arguments.of(EndsWithValidator(), "123test", false, null, "test"),
            Arguments.of(EndsWithValidator(), "test123", true, "test: Value does not end with 'test'", "test")
        )

        // String Length Validator Test Cases
        @JvmStatic
        fun lengthValidatorTestCases(): Stream<Arguments> = Stream.of(
            // MinLengthValidator
            Arguments.of(MinLengthValidator(), "test123", false, null, "5"),
            Arguments.of(MinLengthValidator(), "test", true, "test: Value length (4) is less than minimum required length (5)", "5"),
            
            // MaxLengthValidator
            Arguments.of(MaxLengthValidator(), "test", false, null, "5"),
            Arguments.of(MaxLengthValidator(), "test123", true, "test: Value length (7) exceeds maximum allowed length (5)", "5")
        )

        // Boolean Validator Test Cases
        @JvmStatic
        fun booleanValidatorTestCases(): Stream<Arguments> = Stream.of(
            // IsTrueValidator
            Arguments.of(IsTrueValidator(), true, false, null),
            Arguments.of(IsTrueValidator(), false, true, "test: Value is false when it should be true"),
            
            // IsFalseValidator
            Arguments.of(IsFalseValidator(), false, false, null),
            Arguments.of(IsFalseValidator(), true, true, "test: Value is true when it should be false")
        )
    }
} 