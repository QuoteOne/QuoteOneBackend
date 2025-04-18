//package com.app.repository.material.repository.models
//
//import com.app.annotations.Form
//import com.app.annotations.Label
//import com.app.repository.common.Product
//import com.fasterxml.jackson.annotation.JsonProperty
//import jakarta.persistence.*
//import kotlin.jvm.Transient
//
//@Entity
//@Table(name = "processing")
//@Form(name = "processing")
//class Processing(
//    @Transient
//    val category: String,
//
//    @Label(name = "名稱")
//    override var label: String,
//
//    @Label(name = "貨號")
//    override var sku: String,
//
//    @Label(name = "描述")
//    override var description: String,
//
//    @Label("加工類型")
//    var processingType: String? = null,
//
//
//) : Product(
//    label = label,
//    sku = sku,
//    description = description,
//) {
//    @PostLoad
//    fun init() {
//        super.apply(super.label, super.sku, super.description)
//    }
//}