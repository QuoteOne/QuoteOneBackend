package com.app.repository.identity.company

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "company")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class BaseCompany (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val name: String,
    val taxId: String,
    val address: String,
    val createdAt: LocalDateTime,
    var licenseNumber: String?,
    var website: String?
) {
    constructor(name: String,
                taxId: String,
                address: String,
                createdAt: LocalDateTime,
                licenseNumber: String?,
                website: String?):
            this(
                id = null,
                name = name,
                taxId = taxId,
                address = address,
                createdAt = createdAt,
                licenseNumber = licenseNumber,
                website = website
    )
}