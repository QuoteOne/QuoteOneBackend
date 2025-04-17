package com.app.repository.common

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface IProductRepository: JpaRepository<Product, UUID>


@Repository
interface IPricingProfileRepository: JpaRepository<PricingProfile, UUID>