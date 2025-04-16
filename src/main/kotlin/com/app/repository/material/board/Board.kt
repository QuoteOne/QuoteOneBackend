package com.app.repository.material.board

import com.app.annotations.Form
import com.app.annotations.Label
import com.app.repository.common.Product
import jakarta.persistence.Entity
import java.util.*

@Entity
@Form(name = "board")
class Board(

    @Transient
    val category: String,

    override val id: UUID? = null,
    @Label(name = "名稱")
    override val label: String,

    @Label(name = "貨號")
    override val sku: String,

    @Label(name = "描述")
    override val description: String,

    @Label("深度")
    val depth: Double? = null,

    @Label("寬度")
    val width: Double? = null,

    @Label("高度")
    val height: Double? = null,
): Product(
    label =  label,
    sku = sku,
    description = description,
)