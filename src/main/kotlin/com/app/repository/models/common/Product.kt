package com.app.repository.models.common

import jakarta.persistence.*
import java.util.UUID


@Entity
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val name: String,
    val price: Double,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: ProductCategory,

    val description: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "values_id")
    val entityValues: EntityValues

)