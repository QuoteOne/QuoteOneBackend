package com.app.service.mapper

import com.app.repository.common.Product
import org.springframework.stereotype.Service

interface IMaterialMapper {
    fun <T> mapTo(product: Product, targetEntity: Class<T>): T
}

@Service
class MaterialMapper: IMaterialMapper {
    override fun <T> mapTo(product: Product, targetEntity: Class<T>): T {
        TODO("Not yet implemented")
    }
}