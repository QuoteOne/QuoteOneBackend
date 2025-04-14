package com.app.service.valdator.impl

import com.app.repository.models.ValidationPolicy
import com.app.service.valdator.FormatType
import com.app.service.valdator.Validation
import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType
import java.util.regex.Pattern

class FormatValidator : BaseValidator(
    validationType = ValidationType.FORMAT_VALIDATION,
    supportedTypes = setOf(ValueType.STRING)
) {
    
    override fun validate(policy: ValidationPolicy, value: Any): Validation {
        validateType(policy, value)?.let { return it }
        
        val formatType = try {
            FormatType.valueOf(policy.value.uppercase())
        } catch (e: IllegalArgumentException) {
            return Validation(
                failed = true,
                reason = "${policy.name}: Invalid format type. Supported formats: ${FormatType.entries.joinToString()}"
            )
        }
        
        val stringValue = value as? String ?: return Validation(
            failed = true,
            reason = "${policy.name}: Format validation requires a string value"
        )
        
        return when (formatType) {
            FormatType.EMAIL -> validateEmail(stringValue, policy.name)
            FormatType.URL -> validateUrl(stringValue, policy.name)
            FormatType.DATE -> validateDate(stringValue, policy.name)
            FormatType.TIME -> validateTime(stringValue, policy.name)
            FormatType.DATETIME -> validateDateTime(stringValue, policy.name)
            FormatType.IPV4 -> validateIpv4(stringValue, policy.name)
            FormatType.IPV6 -> validateIpv6(stringValue, policy.name)
            FormatType.UUID -> validateUuid(stringValue, policy.name)
            FormatType.HEX -> validateHex(stringValue, policy.name)
            FormatType.BASE64 -> validateBase64(stringValue, policy.name)
            FormatType.CUSTOM -> validateRegex(stringValue, policy.value, policy.name)
        }
    }
    
    private fun validateEmail(value: String, policyName: String): Validation {
        val pattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        )
        return if (pattern.matcher(value).matches()) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "$policyName: Invalid email format"
            )
        }
    }
    
    private fun validateUrl(value: String, policyName: String): Validation {
        val pattern = Pattern.compile(
            "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$"
        )
        return if (pattern.matcher(value).matches()) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "$policyName: Invalid URL format"
            )
        }
    }
    
    private fun validateDate(value: String, policyName: String): Validation {
        val pattern = Pattern.compile(
            "^\\d{4}-\\d{2}-\\d{2}$"
        )
        return if (pattern.matcher(value).matches()) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "$policyName: Invalid date format (YYYY-MM-DD)"
            )
        }
    }
    
    private fun validateTime(value: String, policyName: String): Validation {
        val pattern = Pattern.compile(
            "^\\d{2}:\\d{2}:\\d{2}$"
        )
        return if (pattern.matcher(value).matches()) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "$policyName: Invalid time format (HH:MM:SS)"
            )
        }
    }
    
    private fun validateDateTime(value: String, policyName: String): Validation {
        val pattern = Pattern.compile(
            "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$"
        )
        return if (pattern.matcher(value).matches()) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "$policyName: Invalid datetime format (YYYY-MM-DDTHH:MM:SS)"
            )
        }
    }
    
    private fun validateIpv4(value: String, policyName: String): Validation {
        val pattern = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
        )
        return if (pattern.matcher(value).matches()) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "$policyName: Invalid IPv4 format"
            )
        }
    }
    
    private fun validateIpv6(value: String, policyName: String): Validation {
        val pattern = Pattern.compile(
            "^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$"
        )
        return if (pattern.matcher(value).matches()) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "$policyName: Invalid IPv6 format"
            )
        }
    }
    
    private fun validateUuid(value: String, policyName: String): Validation {
        val pattern = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"
        )
        return if (pattern.matcher(value).matches()) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "$policyName: Invalid UUID format"
            )
        }
    }
    
    private fun validateHex(value: String, policyName: String): Validation {
        val pattern = Pattern.compile(
            "^[0-9a-fA-F]+$"
        )
        return if (pattern.matcher(value).matches()) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "$policyName: Invalid hexadecimal format"
            )
        }
    }
    
    private fun validateBase64(value: String, policyName: String): Validation {
        val pattern = Pattern.compile(
            "^[A-Za-z0-9+/]+={0,2}$"
        )
        return if (pattern.matcher(value).matches()) {
            Validation(failed = false, reason = null)
        } else {
            Validation(
                failed = true,
                reason = "$policyName: Invalid Base64 format"
            )
        }
    }
    
    private fun validateRegex(value: String, pattern: String, policyName: String): Validation {
        return try {
            if (Pattern.compile(pattern).matcher(value).matches()) {
                Validation(failed = false, reason = null)
            } else {
                Validation(
                    failed = true,
                    reason = "$policyName: Value does not match pattern: $pattern"
                )
            }
        } catch (e: Exception) {
            Validation(
                failed = true,
                reason = "$policyName: Invalid regex pattern: $pattern"
            )
        }
    }
} 