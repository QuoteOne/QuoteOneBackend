package com.app.repository.models

import com.app.service.valdator.ValidationType
import com.app.service.valdator.ValueType
import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "validation_policies")
class ValidationPolicy(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "attribute", nullable = false)
    val attribute: String,

    @Column(name = "validation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val validationType: ValidationType,

    @Column(name = "value_type", nullable = false)
    @Enumerated(EnumType.STRING)
    val valueType: ValueType,

    @Column(name = "value", nullable = false)
    val value: String,
)

