package com.app.repository.common


import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "pricing_profiles")
class PricingProfile(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "value", nullable = false)
    var value: Double = 0.0,

    @Column(name = "unit", nullable = false)
    var unit: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product
)