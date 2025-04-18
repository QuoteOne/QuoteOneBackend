package com.app.repository.material.repository.models

import com.app.annotations.Form
import com.app.annotations.Label
import com.app.repository.common.Product
import jakarta.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "drawers")
@Form(name = "drawer")
class Drawer(
    @Transient
    val category: String,

    @Label(name = "名稱")
    override var label: String,

    @Label(name = "貨號")
    override var sku: String,

    @Label(name = "描述")
    override var description: String,

    @Label("樣式")
    var style: String? = null,


) : Product(
    label = label,
    sku = sku,
    description = description,
) {
    @PostLoad
    fun init() {
        super.apply(super.label, super.sku, super.description)
    }
}