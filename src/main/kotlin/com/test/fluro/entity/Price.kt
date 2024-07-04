package com.test.fluro.entity

import jakarta.persistence.*

@Entity
@Table(name = "prices")
data class Price(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "sku", nullable = false)
    var sku: String,

    @Column(name = "unit_price", nullable = false)
    var unitPrice: String
    ) {

    override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Price

    if (id != other.id) return false
    if (sku != other.sku) return false

    return unitPrice == other.unitPrice
  }

  override fun hashCode(): Int {
    return id?.hashCode() ?: 0
  }
}
