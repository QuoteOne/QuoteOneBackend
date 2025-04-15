package com.app.repository.common

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IEntityValuesRepository: JpaRepository<EntityValues, UUID>