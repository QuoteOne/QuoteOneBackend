package com.app.service

import com.app.repository.common.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

interface IProductService {
    fun getDefaultCategory(): ProductCategory
    fun addProduct(label: String, description: String): Product
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
    override fun addProduct(label: String, description: String): Product {
        val defaultCategory = getDefaultCategory()

        val product = Product(
            description = description,
            label = label,
            category = defaultCategory,
            attributes = EntityValues.empty()
        )

        return productRepository.save(product)
    }

    override fun assignCategory(productId: UUID, categoryId: UUID): Product {
        TODO("Not yet implemented")
    }

}