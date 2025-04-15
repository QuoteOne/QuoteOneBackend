package com.app.repository.identity.company

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "company")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class BaseCompany (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "tax_id", nullable = true)
    val taxId: String,

    @Column(name = "address", nullable = true)
    val address: String,


    @Column(name = "license_number", nullable = true)
    var licenseNumber: String?,

    @Column(name = "website", nullable = true)
    var website: String?,


    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Date? = null,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
) {
    constructor(name: String,
                taxId: String,
                address: String,
                licenseNumber: String?,
                website: String?):
            this(
                id = null,
                name = name,
                taxId = taxId,
                address = address,
                licenseNumber = licenseNumber,
                website = website
    )
}