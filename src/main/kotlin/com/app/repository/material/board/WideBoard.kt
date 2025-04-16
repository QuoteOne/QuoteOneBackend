package com.app.repository.material.board

import com.app.annotations.Form
import com.app.annotations.Label
import com.app.repository.common.Product
import jakarta.persistence.Entity
import java.util.*

@Entity
@Form(name = "wide-board")
class WideBoard(

    @Transient
    val category: String = "wide-board",

    override val id: UUID? = null,
    @Label(name = "名稱")
    override val label: String,

    @Label(name = "貨號")
    override val sku: String,

    @Label(name = "描述")
    override val description: String,

    @Label("深度")
    val depth: Double,

    @Label("寬度")
    val width: Double,

    @Label("高度")
    val height: Double,
): Product(
    label =  label,
    sku = sku,
    description = description,
)