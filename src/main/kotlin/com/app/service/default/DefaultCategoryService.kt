package com.app.service.default

import com.app.repository.common.IProductCategoryGroupRepository
import com.app.repository.common.IProductCategoryRepository
import com.app.repository.common.ProductCategory
import com.app.repository.common.ProductCategoryGroup
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.io.InputStream




data class CategoryGroup(
    val group: String,
    val items: List<CategoryItem>
)

data class CategoryItem(
    val slug: String,
    val label: String,
    val description: String
)

data class CategoryGroupWrapper(
    val default: ArrayList<CategoryGroup>
)



@Configuration
class DefaultCategoryFileConfig(
    val resourceLoader: ResourceLoader
) {


    @Bean("default-categories")
    fun defaultConfigFile(): InputStream {
        val resource = resourceLoader.getResource("classpath:default_categories.yaml")
        return resource.inputStream
    }
}


@Service
class DefaultCategoryService(
    @Qualifier("default-categories")
    private val defaultCategoryConfig: InputStream,

    @Qualifier("Yaml")
    private val yamlMapper: ObjectMapper,

    private val categoryRepository: IProductCategoryRepository,
    private val categoryGroupRepository: IProductCategoryGroupRepository
) {
    companion object {
        const val DEFAULT = "DEFAULT"
    }

    @PostConstruct
    fun init() {
        insertDefaultGroupAndCategory()
        val groups = getCategoryGroups()
        populateGroups(groups)
    }

    private fun insertDefaultGroupAndCategory() {
        val group = categoryGroupRepository.findBySlug(DEFAULT)
            ?: categoryGroupRepository.save(
                ProductCategoryGroup(slug = DEFAULT, label = DEFAULT, description = "")
            )

        if (!categoryRepository.existsByGroupSlugAndSlug(DEFAULT, DEFAULT)) {
            categoryRepository.save(
                ProductCategory(group = group, slug = DEFAULT, label = DEFAULT, description = "")
            )
        }
    }

    fun populateGroups(groups: Iterable<CategoryGroup>) {
        groups.forEach { categoryGroup ->
            val groupSlug = categoryGroup.group
            val group = categoryGroupRepository.findBySlug(groupSlug)
                ?: categoryGroupRepository.save(
                    ProductCategoryGroup(slug = groupSlug, label = groupSlug, description = "")
                )

            categoryGroup.items.forEach { item ->
                val exists = categoryRepository.existsByGroupSlugAndSlug(groupSlug, item.slug)
                if (!exists) {
                    val category = ProductCategory(
                        group = group,
                        slug = item.slug,
                        label = item.label,
                        description = item.description
                    )
                    categoryRepository.save(category)
                }
            }
        }
    }

    fun getCategoryGroups(): Iterable<CategoryGroup> {
        return yamlMapper.readValue(defaultCategoryConfig, CategoryGroupWrapper::class.java).default
    }
}