package com.app.repository.common

import jakarta.persistence.*
import java.util.UUID


@Entity
@Table(
    name = "product_categories",
    uniqueConstraints = [
        UniqueConstraint(name = "slug-label-unique", columnNames = ["slug", "label"])
    ],
    indexes = [
        Index(columnList = "group_name,slug")
    ]
)
class ProductCategory (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,


    @Column(name = "group_name", nullable = false)
    var groupName: String,


    @Column(name = "slug", nullable = false)
    var slug: String,

    @Column(name = "label", nullable = false)
    var label: String,

    @Column(name = "description", nullable = true)
    var description: String,

    @OneToMany(mappedBy = "primaryCategory", cascade = [CascadeType.ALL], orphanRemoval = true)
    val products: MutableList<Product> = mutableListOf()

) {
    constructor(groupName: String, slug: String, label: String, description: String): this(
        id = null,
        groupName = groupName,
        label= label,
        slug = slug,
        description = description
    )
}