package com.app.repository.common

import jakarta.persistence.*
import java.util.UUID


@Entity
@Table(
    name = "product_category_groups",
    uniqueConstraints = [
        UniqueConstraint(name = "group_slug_unique", columnNames = ["slug"])
    ]
)
class ProductCategoryGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "slug", nullable = false, unique = true)
    var slug: String,

    @Column(name = "label", nullable = false)
    var label: String,

    @Column(name = "description")
    var description: String? = null,

    @OneToMany(mappedBy = "group", cascade = [CascadeType.ALL], orphanRemoval = true)
    val categories: MutableList<ProductCategory> = mutableListOf()
) {
    constructor(slug: String, label: String, description: String?) : this(
        id = null,
        slug = slug,
        label = label,
        description = description
    )
}

@Entity
@Table(
    name = "product_categories",
    uniqueConstraints = [
        UniqueConstraint(name = "group_slug_label_unique", columnNames = ["group_id", "slug", "label"])
    ],
    indexes = [
        Index(columnList = "group_id,slug")
    ]
)
class ProductCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    var group: ProductCategoryGroup,

    @Column(name = "slug", nullable = false)
    var slug: String,

    @Column(name = "label", nullable = false)
    var label: String,

    @Column(name = "description")
    var description: String? = null,

    @OneToMany(mappedBy = "primaryCategory", cascade = [CascadeType.ALL], orphanRemoval = true)
    val products: MutableList<Product> = mutableListOf()
) {
    constructor(group: ProductCategoryGroup, slug: String, label: String, description: String?) : this(
        id = null,
        group = group,
        slug = slug,
        label = label,
        description = description
    )
}
