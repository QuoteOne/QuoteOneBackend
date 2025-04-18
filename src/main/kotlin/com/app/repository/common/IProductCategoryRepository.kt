package com.app.repository.common

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID


@Repository
interface IProductCategoryGroupRepository : JpaRepository<ProductCategoryGroup, UUID> {
    fun findBySlug(slug: String): ProductCategoryGroup?
}


@Repository
interface IProductCategoryRepository: JpaRepository<ProductCategory, UUID> {
    fun existsByGroupSlugAndSlug(groupSlug: String, slug: String): Boolean
    fun existsBySlug(slug: String): Boolean
    fun findBySlug(slug: String): ProductCategory?
}