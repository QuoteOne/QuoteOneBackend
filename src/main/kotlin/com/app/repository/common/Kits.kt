package com.app.repository.common

import com.app.annotations.Form
import com.app.annotations.Label
import jakarta.persistence.*
import kotlin.jvm.Transient


enum class KitsType(val value: String) {
    MUST_HAVE("MUST_HAVE"),
    OPTIONAL("OPTIONAL"),
}

@Entity
@Form(name = "kits")
@Table(name = "kits")
class Kits(
    @Transient
    val category: String,

    @Label(name = "名稱")
    override var label: String,

    @Label(name = "貨號")
    override var sku: String,

    @Label(name = "描述")
    override var description: String,

    @Label(name = "選購類型")
    @Enumerated(EnumType.STRING)
    val type: KitsType = KitsType.OPTIONAL
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