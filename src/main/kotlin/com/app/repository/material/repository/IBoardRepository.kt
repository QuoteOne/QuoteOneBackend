package com.app.repository.material.repository

import com.app.repository.material.repository.models.Board
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IBoardRepository: JpaRepository<Board, UUID> {
}