package com.app.repository.models.company

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*


@MappedSuperclass
class BaseCompany (
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