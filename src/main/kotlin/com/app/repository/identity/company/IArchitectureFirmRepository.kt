package com.app.repository.identity.company

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IArchitectureFirmRepository: JpaRepository<ArchitectureFirm, UUID>