package com.test.fluro.repository;

import com.test.fluro.entity.Special
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SpecialRepository : JpaRepository<Special, Long> {

    @Query("SELECT new Special(s.id, s.sku, s.nSku, s.oneFree, s.specialPrice, s.specialPriceDesc) FROM Special s WHERE s.sku = :sku")
    fun findBySku(sku: String?): Special?

    @Query("SELECT new Special(s.id, s.sku, s.nSku, s.oneFree, s.specialPrice, s.specialPriceDesc) FROM Special s ORDER BY s.sku")
    fun findAllSpecials(): List<Special>
}