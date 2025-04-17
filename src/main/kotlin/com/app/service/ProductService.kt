package com.app.service

import com.app.CategorySlugNotFound
import com.app.repository.common.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

interface IProductService {
    fun getDefaultCategory(): ProductCategory
    fun addProduct(label: String, sku: String, description: String): Product
    fun addProductWithCategorySlug(product: Product, categorySlug: String): Product
    fun assignCategory(productId: UUID, categoryId: UUID): Product
}


@Service
class ProductService(
    val productCategoryRepository: IProductCategoryRepository,
    val productRepository: IProductRepository,
): IProductService {

    val DEFAULT = "DEFAULT"


    override fun getDefaultCategory(): ProductCategory {
        return productCategoryRepository.findBySlug(DEFAULT)!!
    }

    @Transactional
    override fun addProduct(label: String, sku: String,description: String): Product {
        val defaultCategory = getDefaultCategory()

        val product = Product(
            description = description,
            label = label,
            primaryCategory = defaultCategory,
            sku = sku,
        )

        return productRepository.save(product)
    }

    @Transactional
    override fun addProductWithCategorySlug(product: Product, categorySlug: String): Product {
        productCategoryRepository.findBySlug(categorySlug)?.let { category ->
            product.primaryCategory = category
        } ?: run {
            throw CategorySlugNotFound
        }


        return productRepository.save(product)
    }

    @Transactional
    override fun assignCategory(productId: UUID, categoryId: UUID): Product {
        TODO("Not yet implemented")
    }

}