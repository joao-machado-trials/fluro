package com.test.fluro.repository;

import com.test.fluro.entity.Special
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SpecialRepository : JpaRepository<Special, Long> {

    @Query("SELECT new Special(s.id, s.sku, s.nSku, s.specialPrice, s.specialPriceDesc, s.promotion) FROM Special s WHERE s.sku = :sku")
    fun findBySku(sku: String?): Special?
}