package com.test.fluro.service

import org.springframework.ui.Model

interface OrderService {

    fun init(model : Model) : Model

    fun order(sku: String, quanItem: Int)

    fun applyOneFree(sku: String)

    fun applyMultiPriced(sku: String)

    fun applyMealDeal(sku: String)

    fun price(sku: String, unitPrice: String)

    fun specials(sku: String, nSku: String, specialPrice: String, specialPriceDesc: String, promotion: String)
}
