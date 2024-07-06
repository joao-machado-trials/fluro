package com.test.fluro.repository;

import com.test.fluro.entity.Item
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long> {

    @Query("SELECT new Item(i.id, i.sku, i.skuQuant, i.skuTotal) FROM Item i WHERE i.sku = :sku")
    fun findBySku(sku: String?): Item?

    @Modifying
    @Transactional
    @Query("DELETE FROM Item i WHERE i.sku = :sku")
    fun deleteBySku(sku: String?)
}