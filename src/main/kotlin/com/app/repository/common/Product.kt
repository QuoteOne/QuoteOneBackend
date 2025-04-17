package com.app.repository.common

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.util.*


interface ProductAware {
    var label: String
    var sku: String
    var description: String

    fun apply(baseLabel: String, baseSku: String, baseDescription: String) {
        label = baseLabel
        sku = baseSku
        description = baseDescription
    }
}

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(name = "label", nullable = false)
    @JsonProperty("label")
    override var label: String,


    @Column(name = "description", nullable = true)
    @JsonProperty("description")
    override var description: String,

    @Column(name = "sku", nullable = false, unique = true)
    @JsonProperty("sku")
    override var sku: String,


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    var primaryCategory: ProductCategory? = null,


    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "values_id")
    val attributes: EntityValues,

    // 使用多對多關聯來支持一個產品屬於多個分類
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "product_category_association", // 關聯表名稱
        joinColumns = [JoinColumn(name = "product_id")], // 產品ID
        inverseJoinColumns = [JoinColumn(name = "category_id")] // 類別ID
    )
    val additionalCategories: MutableList<ProductCategory> = mutableListOf(),


    @OneToMany
    @JoinTable(
        name = "kits_products",
        joinColumns = [JoinColumn(name = "id")],
        inverseJoinColumns = [JoinColumn(name = "kit_product_id")]
    )
    val kits: MutableSet<Product> = mutableSetOf(),
): ProductAware {
    constructor(
        label: String,
        sku: String,
        description: String,
        primaryCategory: ProductCategory? = null,
        attributes: EntityValues = EntityValues.empty()
    ): this(
        id = null,
        label = label,
        description = description,
        sku = sku,
        primaryCategory = primaryCategory,
        attributes = attributes
    )
}