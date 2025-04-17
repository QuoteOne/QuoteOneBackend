package com.app.controller

import com.app.repository.common.Product
import com.app.repository.material.repository.models.Board
import com.app.repository.material.repository.models.DoorPanel
import com.app.repository.material.repository.models.Drawer
import com.app.repository.material.repository.models.Processing
import com.app.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
@RequestMapping("/api/private/products")
class ProductController(
    val productService: ProductService
) {

    data class UncategorizedProductCreate(
        val label: String,
        val sku: String,
        val description: String
    )

    data class ProductCategoryChange(
        val productId: UUID,
        val targetCategoryId: UUID
    )

    data class KitProductAdd(
        val productId: UUID,
        val kitId: UUID
    )

    data class KitProductRemove(
        val productId: UUID,
        val kitId: UUID
    )

    @PostMapping
    fun addUncategorizedProduct(@RequestBody product: UncategorizedProductCreate): ResponseEntity<Product> {
        val savedProduct =productService.addProduct(product.label, product.sku, product.description)
        return ResponseEntity.ok(savedProduct)
    }

    @PostMapping("/board")
    fun addBoard(@RequestBody product: Board): ResponseEntity<Product> {
        val savedProduct = productService.addProductWithCategorySlug(product, product.category)
        return ResponseEntity.ok(savedProduct)
    }

    @PostMapping("/door-panel")
    fun addDoorPanel(@RequestBody product: DoorPanel): ResponseEntity<Product> {
        val savedProduct = productService.addProductWithCategorySlug(product, product.category)
        return ResponseEntity.ok(savedProduct)
    }

    @PostMapping("/drawer")
    fun addDrawer(@RequestBody product: Drawer): ResponseEntity<Product> {
        val savedProduct = productService.addProductWithCategorySlug(product, product.category)
        return ResponseEntity.ok(savedProduct)
    }

    @PostMapping("/processing")
    fun addProcessing(@RequestBody product: Processing): ResponseEntity<Product> {
        val savedProduct = productService.addProductWithCategorySlug(product, product.category)
        return ResponseEntity.ok(savedProduct)
    }

    @PostMapping("/change-category")
    fun changeProductCategory(@RequestBody change: ProductCategoryChange): ResponseEntity<Product> {
        val savedProduct = productService.changeProductCategory(change.productId, change.targetCategoryId)
        return ResponseEntity.ok(savedProduct)
    }

    @PostMapping("/add-kit")
    fun addKitToProduct(@RequestBody kit: KitProductAdd): ResponseEntity<Product> {
        val savedProduct = productService.addKitToProduct(kit.productId, kit.kitId)
        return ResponseEntity.ok(savedProduct)
    }

    @PostMapping("/remove-kit")
    fun removeKitFromProduct(@RequestBody kit: KitProductRemove): ResponseEntity<Product> {
        val savedProduct = productService.removeKitFromProduct(kit.productId, kit.kitId)
        return ResponseEntity.ok(savedProduct)
    }
}