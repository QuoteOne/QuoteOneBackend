package com.app.repository.material.repository

import com.app.repository.material.repository.models.DoorPanel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IDoorPanelRepository: JpaRepository<DoorPanel, UUID>