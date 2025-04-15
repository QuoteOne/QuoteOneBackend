package com.app.repository.common

import jakarta.persistence.*
import java.util.UUID


@Entity
@Table(
    name = "product_category",
    uniqueConstraints = [
        UniqueConstraint(name = "slug-label-unique", columnNames = ["slug", "label"])
    ],
)
class ProductCategory (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,


    @Column(name = "group", nullable = false)  // group 用來儲存大分類名稱
    val group: String,  // group 可以是大分類的名稱（例如：板材類別、門板與相關構件）

    @Column(name = "slug", nullable = false)
    val slug: String,

    @Column(name = "label", nullable = false)
    val label: String,

    @Column(name = "description", nullable = true)
    val description: String,

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], orphanRemoval = true)
    val products: MutableList<Product> = mutableListOf()
)