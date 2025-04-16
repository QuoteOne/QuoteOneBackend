package com.app.controller

import com.app.repository.common.Product
import com.app.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController




@RestController
@RequestMapping("/api/admin/products")
class ProductController(
    val productService: ProductService
) {


    data class ProductCreate(
        val label: String,
        val description: String
    )

    @PostMapping
    fun addProduct(@RequestBody product: ProductCreate): ResponseEntity<Product> {
        val savedProduct =productService.addProduct(product.label, product.description)
        return ResponseEntity.ok(savedProduct)
    }
}