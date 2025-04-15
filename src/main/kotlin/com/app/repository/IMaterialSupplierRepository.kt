package com.app.repository

import com.app.repository.models.identity.company.MaterialSupplier
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IMaterialSupplierRepository: JpaRepository<MaterialSupplier, UUID>