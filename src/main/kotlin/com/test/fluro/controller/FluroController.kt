package com.test.fluro.controller

import com.test.fluro.entity.Item
import com.test.fluro.entity.Price
import com.test.fluro.entity.Special
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import com.test.fluro.repository.ItemRepository
import com.test.fluro.repository.PriceRepository
import com.test.fluro.repository.SpecialRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class FluroController {

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @Autowired
    private lateinit var priceRepository: PriceRepository

    @Autowired
    private lateinit var specialRepository: SpecialRepository

    @GetMapping("/order")
    fun landingPage(model: Model): String {

        val skus = listOf("A", "B", "C", "D", "E")
        model.addAttribute("skus", skus)
        val quant = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        model.addAttribute("quant", quant)
        val items = itemRepository.findAllItems()
        model.addAttribute("items", items)
        var bill = 0
        for (item in items)
            bill += item.skuTotal.toInt()
        model.addAttribute("bill", bill)
        val prices = priceRepository.findAllPrices()
        model.addAttribute("prices", prices)
        val specials = specialRepository.findAllSpecials()
        model.addAttribute("specials", specials)

        return "order"
    }

    @PostMapping("/order")
    fun order(model: Model, @RequestParam("skuItem") sku: String, @RequestParam("quanItem") quanItem: Int): String {

        var item = itemRepository.findBySku(sku)
        val price = priceRepository.findBySku(sku)

        if (price != null) {
            item = if (item != null) {
                Item(item.id, sku, item.skuQuant + quanItem,
                    ((item.skuQuant + quanItem) * price.unitPrice.toInt()).toString()
                )
            } else {
                Item(null, sku, quanItem, (quanItem * price.unitPrice.toInt()).toString())
            }
            itemRepository.save(item)
        }

        return "redirect:/order"
    }

    @PostMapping("/price/{sku}")
    fun price(model: Model,
                @RequestParam("sku") sku: String,
                @RequestParam("unitPrice") unitPrice: String): String {

        try {
            unitPrice.toInt()
            var price = priceRepository.findBySku(sku)
            if (price != null) {
                price = Price(price.id, price.sku, unitPrice)
                priceRepository.save(price)
                itemRepository.deleteBySku(sku)
            }
        } catch (e: NumberFormatException) {
            println("Number Format error")
        }

        return "redirect:/order"
    }

    @PostMapping("/specials/{sku}")
    fun specials(model: Model,
                    @RequestParam("skuSpecial") sku: String,
                    @RequestParam("nSku") nSku: String,
                    @RequestParam("oneFree") oneFree: Boolean,
                    @RequestParam("specialPrice") specialPrice: String,
                    @RequestParam("specialPriceDesc") specialPriceDesc: String): String {

        try {
            specialPrice.toInt()
            var special = specialRepository.findBySku(sku)
            if (special != null) {
                special = Special(special.id, special.sku, nSku, oneFree, specialPrice, specialPriceDesc)
                specialRepository.save(special)
                itemRepository.deleteBySku(sku)
            }
        } catch (e: NumberFormatException) {
            println("Number Format error")
        }

        return "redirect:/order"
    }
}
