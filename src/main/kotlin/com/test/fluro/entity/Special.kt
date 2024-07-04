package com.test.fluro.entity

import jakarta.persistence.*

@Entity
@Table(name = "specials")
data class Special(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "sku")
    var sku: String,

    @Column(name = "n_sku")
    var nSku: String,

    @Column(name = "one_free")
    var oneFree: Boolean,

    @Column(name = "special_price")
    var specialPrice: String,

    @Column(name = "special_price_desc")
    var specialPriceDesc: String
    ) {

    override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Special

    if (id != other.id) return false
    if (sku != other.sku) return false
    if (nSku != other.nSku) return false
    if (oneFree != other.oneFree) return false
    if (specialPrice != other.specialPrice) return false

    return specialPriceDesc == other.specialPriceDesc
  }

  override fun hashCode(): Int {
    return id?.hashCode() ?: 0
  }
}
