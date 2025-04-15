package com.app.repository.common

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.UUID


@Entity
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val label: String,

    val description: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "values_id")
    val attributes: EntityValues,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: ProductCategory,
) {
    fun getAttribute(attribute: String): Any? {
        return attributes.values[attribute]
    }
}