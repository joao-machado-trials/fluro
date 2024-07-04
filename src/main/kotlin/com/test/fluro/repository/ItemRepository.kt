package com.test.fluro.repository;

import com.test.fluro.entity.Item
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface ItemRepository : JpaRepository<Item, Long> {

    @Query("SELECT new Item(i.id, i.sku, i.skuQuant, p.unitPrice) FROM Item i INNER JOIN Price p ON i.sku = p.sku WHERE i.sku = :sku")
    fun findBySku(sku: String?): Item?

    @Query("SELECT new Item(i.id, i.sku, i.skuQuant, i.skuTotal) FROM Item i ORDER BY i.sku")
    fun findAllItems(): List<Item>

    @Modifying
    @Transactional
    @Query("DELETE FROM Item i WHERE i.sku = :sku")
    fun deleteBySku(sku: String?)
}