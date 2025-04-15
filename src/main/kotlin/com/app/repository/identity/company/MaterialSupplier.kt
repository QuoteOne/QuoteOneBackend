package com.app.repository.identity.company

import jakarta.persistence.Entity
import jakarta.persistence.PrimaryKeyJoinColumn
import java.time.LocalDateTime


@Entity
@PrimaryKeyJoinColumn(name = "company_id")
class MaterialSupplier(
    name: String,
    taxId: String,
    address: String,
    createdAt: LocalDateTime,
    licenseNumber: String?,
    website: String?
) : BaseCompany(name, taxId, address, createdAt, licenseNumber, website)