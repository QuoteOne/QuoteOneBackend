package com.app.repository.identity.individual

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IDesignerProfileRepository: JpaRepository<DesignerProfile, UUID>