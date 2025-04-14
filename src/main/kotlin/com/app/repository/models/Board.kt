package com.app.repository.models

import com.app.service.valdator.IValidationRequired
import com.app.service.valdator.ValidationPolicy


// SKU stands for Stock Keeping Unit. It is a unique identifier assigned by a company to track a product in inventory, sales, and logistics.
// SKUs are used internally by companies and are often custom-designed to encode useful information
// such as: Product type, Size, Color, Variant Location or warehouse info
// Example: TSHIRT-BLK-MED might refer to a black T-shirt in medium size.

class BaseBoard(
    val name: String,
    val sku: String,
    val length: Double,
    val width: Double,
    val height: Double,
    override val validationPolicies: Iterable<ValidationPolicy> = mutableListOf(),
    override val values: Map<String, Any> = mutableMapOf()
): IValidationRequired {
    init {
        validate()
    }
}