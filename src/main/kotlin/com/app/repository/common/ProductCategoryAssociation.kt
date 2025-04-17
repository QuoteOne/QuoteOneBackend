package com.app.repository.common

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "product_category_associations",
    uniqueConstraints = [
        UniqueConstraint(
            name = "category_association_unique",
            columnNames = [
                "category_id",
                "associated_category_id"
            ])
    ])
class ProductCategoryAssociation(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "category_id")
    val category: ProductCategory,

    @ManyToOne
    @JoinColumn(name = "associated_category_id")
    val associatedCategory: ProductCategory,


    @Column(name = "association_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val relationshipType: AssociationType
)