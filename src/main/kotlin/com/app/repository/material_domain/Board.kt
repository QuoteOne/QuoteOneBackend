package com.app.repository.material_domain

import com.app.repository.common.EntityValues
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.util.*


// SKU stands for Stock Keeping Unit. It is a unique identifier assigned by a company to track a product in inventory, sales, and logistics.
// SKUs are used internally by companies and are often custom-designed to encode useful information
// such as: Product type, Size, Color, Variant Location or warehouse info
// Example: TSHIRT-BLK-MED might refer to a black T-shirt in medium size.

@Entity
@Table(name = "boards")
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    val id: UUID?,

    @Column(name = "name")
    val name: String,

    @Column(name = "sku", nullable = false)
    val sku: String,

    @Column(name = "length", nullable = false)
    val length: Double,

    @Column(name = "width", nullable = false)
    val width: Double,

    @Column(name = "height", nullable = false)
    val height: Double,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "values_id")
    val entityValues: EntityValues
) {

}