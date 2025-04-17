package com.app.repository.common

import jakarta.persistence.*
import java.util.UUID



@Entity
@Table(name = "product_associations",
    uniqueConstraints = [
        UniqueConstraint(
            name = "product_association_unique",
            columnNames = [
                "product_id",
                "associated_product_id"
            ])
    ])
class ProductAssociation(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(name = "product_id")
    val product: Product,

    @ManyToOne
    @JoinColumn(name = "associated_product_id")
    val associatedProduct: Product,


    @Column(name = "association_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val relationshipType: AssociationType
)