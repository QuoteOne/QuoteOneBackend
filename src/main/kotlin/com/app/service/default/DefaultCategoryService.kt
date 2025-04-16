package com.app.service.default

import com.app.repository.common.IProductCategoryRepository
import com.app.repository.common.ProductCategory
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
    val defaultCategoryConfig: InputStream,
    @Qualifier("Yaml")
    val yamlMapper: ObjectMapper,

    val categoryRepository: IProductCategoryRepository
) {

    @PostConstruct
    fun init() {
        val groups = getCategoryGroups()
        populateGroups(groups)
    }


    fun populateGroups(groups: Iterable<CategoryGroup>) {
        groups.forEach outer@{ categoryGroup ->
            categoryGroup.items.forEach {
                val isExist = categoryRepository.existsProductCategoryBySlug(it.slug)
                if (isExist) return@outer
                val category = ProductCategory(groupName = categoryGroup.group, slug = it.slug, label = it.label, description = it.description)
                categoryRepository.save(category)
            }
        }
    }


    fun getCategoryGroups(): Iterable<CategoryGroup> {
        val wrapper = yamlMapper.readValue(defaultCategoryConfig, CategoryGroupWrapper::class.java)
        return wrapper.default

    }
}