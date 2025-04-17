package com.app.repository.material.repository

import com.app.repository.material.repository.models.Drawer
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IDrawerRepository: JpaRepository<Drawer, UUID>