package com.app.repository.common

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
interface IProductCategoryRepository: JpaRepository<ProductCategory, UUID> {
    fun existsProductCategoryBySlug(slug: String): Boolean
}