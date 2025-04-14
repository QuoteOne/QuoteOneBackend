package com.app.repository

import com.app.repository.models.EntityValues
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IEntityValuesRepository: JpaRepository<EntityValues, UUID>