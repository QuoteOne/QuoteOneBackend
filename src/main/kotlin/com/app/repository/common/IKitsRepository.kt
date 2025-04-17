package com.app.repository.common

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IKitsRepository:JpaRepository<Kits, UUID>