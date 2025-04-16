package com.app.repository.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.util.UUID


@Entity
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "label", nullable = false)
    val label: String,


    @Column(name = "description", nullable = true)
    val description: String,


    @JsonProperty("attributes")
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "values_id")
    val attributes: EntityValues = EntityValues.empty(),

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: ProductCategory,


    // 使用多對多關聯來支持一個產品屬於多個分類
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "product_category_association", // 關聯表名稱
        joinColumns = [JoinColumn(name = "product_id")], // 產品ID
        inverseJoinColumns = [JoinColumn(name = "category_id")] // 類別ID
    )
    val categories: MutableList<ProductCategory> = mutableListOf()

) {
    fun getAttribute(attribute: String): Any? {
        return attributes.values[attribute]
    }
}