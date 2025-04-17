package com.app.service

import com.app.*
import com.app.repository.common.*
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

interface IProductService {
    fun getDefaultCategory(): ProductCategory
    fun addProduct(label: String, sku: String, description: String): Product
    fun addProductWithCategorySlug(product: Product, categorySlug: String): Product
    fun changeProductCategory(productId: UUID, categoryId: UUID): Product
    fun addKitToProduct(productId: UUID, kitsId: UUID): Product
    fun removeKitFromProduct(productId: UUID, kitsId: UUID): Product
}


@Service
class ProductService(
    val productCategoryRepository: IProductCategoryRepository,
    val productRepository: IProductRepository,
    val kitsRepository: IKitsRepository
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

    fun isSameCategoryGroup(
        productCategory: ProductCategory,
        targetCategory: ProductCategory
    ): Boolean {
        return productCategory.groupName == targetCategory.groupName
    }

    @Transactional
    override fun changeProductCategory(productId: UUID, categoryId: UUID): Product {
        val targetCategory = productCategoryRepository.findById(categoryId).getOrNull() ?: throw CategoryNotFound
        val product = productRepository.findById(productId).getOrNull() ?: throw ProductNotFound


        if (!isSameCategoryGroup(product.primaryCategory!!, targetCategory)) {
            throw CategoryGroupNotSame
        }


        product.primaryCategory = targetCategory
        return productRepository.save(product)
    }

    override fun addKitToProduct(productId: UUID, kitsId: UUID): Product {
        val product = productRepository.findById(productId).getOrNull() ?: throw ProductNotFound
        val kit = kitsRepository.findById(kitsId).getOrNull() ?: throw KitProductNotFound

        if (product.kits.contains(kit)) {
            throw KitAlreadyExistsInProduct
        }
        product.kits.add(kit)
        return productRepository.save(product)
    }

    override fun removeKitFromProduct(productId: UUID, kitsId: UUID): Product {
        val product = productRepository.findById(productId).getOrNull() ?: throw ProductNotFound
        val kit = productRepository.findById(kitsId).getOrNull() ?: throw KitProductNotFound

        if (!product.kits.contains(kit)) {
            throw KitNotFoundInProduct
        }
        product.kits.remove(kit)
        return productRepository.save(product)
    }
}