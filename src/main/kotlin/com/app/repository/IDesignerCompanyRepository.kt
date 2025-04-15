package com.app.repository

import com.app.repository.models.company.ArchitectureFirm
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IDesignerCompanyRepository: JpaRepository<ArchitectureFirm, UUID>