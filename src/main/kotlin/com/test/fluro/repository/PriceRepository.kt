package com.test.fluro.repository;

import com.test.fluro.entity.Price
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PriceRepository : JpaRepository<Price, Long> {

    @Query("SELECT new Price(p.id, p.sku, p.unitPrice) FROM Price p WHERE p.sku = :sku")
    fun findBySku(sku: String?): Price?
}