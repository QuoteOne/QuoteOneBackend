package com.app.repository.common

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "label", nullable = false)
    val label: String,


    @Column(name = "description", nullable = true)
    val description: String,

    @Column(name = "sku", nullable = false, unique = true)
    val sku: String,


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    var primaryCategory: ProductCategory? = null,

    // 使用多對多關聯來支持一個產品屬於多個分類
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "product_category_association", // 關聯表名稱
        joinColumns = [JoinColumn(name = "product_id")], // 產品ID
        inverseJoinColumns = [JoinColumn(name = "category_id")] // 類別ID
    )
    val additionalCategories: MutableList<ProductCategory> = mutableListOf(),
) {


    constructor(
        label: String,
        sku: String,
        description: String,
        primaryCategory: ProductCategory? = null

    ): this(
        id = null,
        label = label,
        description = description,
        sku = sku,
        primaryCategory = primaryCategory
    )
}