package com.app.service.mapper

import com.app.repository.common.Product
import org.springframework.stereotype.Service

interface IMaterialMapper {
    fun <T> mapTo(product: Product, targetEntity: Class<T>): T
    fun <T> mapToProduct(targetEntity: T): Product
}

@Service
class MaterialMapper: IMaterialMapper {
    override fun <T> mapTo(product: Product, targetEntity: Class<T>): T {
        TODO("Not yet implemented")
    }

    override fun <T> mapToProduct(targetEntity: T): Product {
        TODO("Not yet implemented")
    }


}