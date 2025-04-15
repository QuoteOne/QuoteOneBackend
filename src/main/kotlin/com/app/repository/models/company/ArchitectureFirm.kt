package com.app.repository.models.company

import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "architecture_firms")
class ArchitectureFirm(
    name: String,
    taxId: String,
    address: String,
    createdAt: LocalDateTime,
    licenseNumber: String?,
    website: String?
) : BaseCompany(name, taxId, address, createdAt, licenseNumber, website)