package com.test.fluro.entity

import jakarta.persistence.*

@Entity
@Table(name = "items")
data class Item(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "sku", nullable = false)
    var sku: String,

    @Column(name = "sku_quant", nullable = false)
    var skuQuant: Int,

    @Column(name = "sku_total")
    var skuTotal: String
    ) {

    override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Item

    if (id != other.id) return false
    if (sku != other.sku) return false
    if (skuQuant != other.skuQuant) return false

    return skuTotal == other.skuTotal
  }

  override fun hashCode(): Int {
    return id?.hashCode() ?: 0
  }
}
