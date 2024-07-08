package com.test.fluro.service.impl

import com.test.fluro.entity.Item
import com.test.fluro.entity.Price
import com.test.fluro.entity.Special
import com.test.fluro.repository.ItemRepository
import com.test.fluro.repository.PriceRepository
import com.test.fluro.repository.SpecialRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import com.test.fluro.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model

@Service
@RequiredArgsConstructor
class OrderServiceImpl : OrderService {

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @Autowired
    private lateinit var priceRepository: PriceRepository

    @Autowired
    private lateinit var specialRepository: SpecialRepository

    override fun init(model : Model): Model {

        val skus = listOf("A", "B", "C", "D", "E")
        model.addAttribute("skus", skus)
        val quant = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        model.addAttribute("quant", quant)
        val items = itemRepository.findAll()
        model.addAttribute("items", items)
        var bill = 0
        for (item in items) {
            try {
                bill += item.skuTotal.toInt()
            } catch (e: NumberFormatException) {
                println("Number Format error")
            }
        }
        model.addAttribute("bill", bill)
        val prices = priceRepository.findAll()
        model.addAttribute("prices", prices)
        val specials = specialRepository.findAll()
        model.addAttribute("specials", specials)

        return model
    }

    override fun order(sku: String, quanItem: Int) {

        var item = itemRepository.findBySku(sku)
        val price = priceRepository.findBySku(sku)

        item = if (item != null) {
            Item(item.id, sku, item.skuQuant + quanItem,
                ((item.skuQuant + quanItem) * price!!.unitPrice.toInt()).toString()
            )
        } else {
            Item(null, sku, quanItem, (quanItem * price!!.unitPrice.toInt()).toString())
        }
        itemRepository.save(item)
    }

    override fun applyOneFree(sku: String) {

        val item = itemRepository.findBySku(sku)
        val price = priceRepository.findBySku(sku)
        val special = specialRepository.findBySku(sku)

        if (item != null && special!!.promotion == "2") {

            var check = item.skuQuant
            var flag = true
            val discountOneFree: Int?

            val nSkuIt = special.nSku.trim().split("").filter { it.isNotEmpty() }
            if (nSkuIt.isNotEmpty()) {
                for (skuIt in nSkuIt) {
                    flag = (skuIt == item.sku)
                }

                check = if (flag) check / nSkuIt.size else check

                discountOneFree = check * price!!.unitPrice.toInt()
                item.skuTotal = (item.skuTotal.toInt() - discountOneFree).toString()
                itemRepository.save(item)
            }
        }
    }

    override fun applyMultiPriced(sku: String) {

        val item = itemRepository.findBySku(sku)
        val price = priceRepository.findBySku(sku)
        val special = specialRepository.findBySku(sku)

        if (item != null && special!!.promotion == "1") {

            var check = item.skuQuant
            var flag = true
            var discountMulti = special.specialPrice.toInt()

            val nSkuIt = special.nSku.trim().split("").filter { it.isNotEmpty() }
            if (nSkuIt.isNotEmpty()) {
                for (skuIt in nSkuIt) {
                    flag = (skuIt == item.sku)
                }

                check = if (flag) check / (nSkuIt.size) else check

                discountMulti = (price!!.unitPrice.toInt() * nSkuIt.size - discountMulti) * check
                item.skuTotal = (item.skuTotal.toInt() - discountMulti).toString()
                itemRepository.save(item)
            }
        }
    }

    override fun applyMealDeal(sku: String) {

        val item = itemRepository.findBySku(sku)
        val special = specialRepository.findBySku(sku)

        if (item != null && special!!.promotion == "3") {

            var flag = true
            val discountMealDeal = special.specialPrice.toInt()

            val nSkuIt = special.nSku.trim().split("").filter { it.isNotEmpty() }
            if (nSkuIt.isNotEmpty()) {
                for (skuIt in nSkuIt) {
                    flag = itemRepository.findBySku(skuIt) != null
                    if (!flag) break
                }

                if (flag) {
                    item.skuQuant = 1
                    item.skuTotal = discountMealDeal.toString()
                    item.sku = "Meal Deal - "
                    for (skuIt in nSkuIt) {
                        item.sku += skuIt
                    }
                    itemRepository.deleteBySku(item.sku)
                    item.id = null
                    itemRepository.save(item)

                    for (skuIt in nSkuIt) {

                            val priceSec = priceRepository.findBySku(skuIt)
                            val itemSec = itemRepository.findBySku(skuIt)
                            itemSec!!.skuQuant -= 1
                            itemSec!!.skuTotal = (itemSec.skuTotal.toInt() - priceSec!!.unitPrice.toInt()).toString()
                            if (itemSec.skuQuant <= 0) {
                                itemSec.skuQuant = 0
                                itemSec.skuTotal = 0.toString()
                            }
                            itemRepository.save(itemSec)

                    }
                }
            }
        }
    }

    override fun price(sku: String, unitPrice: String) {

        try {
            unitPrice.toInt()
            var price = priceRepository.findBySku(sku)
            if (price != null) {
                price = Price(price.id, price.sku, unitPrice)
                priceRepository.save(price)
                itemRepository.deleteAll()
            }
        } catch (e: NumberFormatException) {
            println("Number Format error")
        }
    }

    override fun specials(sku: String, nSku: String, specialPrice: String, specialPriceDesc: String, promotion: String) {

        try {
            specialPrice.toInt()
            var special = specialRepository.findBySku(sku)
            if (special != null) {
                special = Special(special.id, special.sku, nSku, specialPrice, specialPriceDesc, promotion)
                specialRepository.save(special)
                itemRepository.deleteAll()
            }
        } catch (e: NumberFormatException) {
            println("Number Format error")
        }
    }
}