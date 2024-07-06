package com.test.fluro.controller

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import com.test.fluro.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller

@Controller
class FluroController {

    @Autowired
    private lateinit var orderService: OrderService

    @GetMapping("/")
    fun landingPage(model: Model): String {

        orderService.init(model)

        return "order"
    }

    @PostMapping("/")
    fun order(model: Model,
                @RequestParam("skuItem") sku: String,
                @RequestParam("quanItem") quanItem: Int): String {

        orderService.order(sku, quanItem)
        orderService.applyMultiPriced(sku)
        orderService.applyOneFree(sku)
        orderService.applyMealDeal(sku)

        return "redirect:/"
    }

    @PostMapping("/price/{sku}")
    fun price(model: Model,
                @RequestParam("sku") sku: String,
                @RequestParam("unitPrice") unitPrice: String): String {

        orderService.price(sku, unitPrice)

        return "redirect:/"
    }

    @PostMapping("/specials/{sku}")
    fun specials(model: Model,
                    @RequestParam("skuSpecial") sku: String,
                    @RequestParam("nSku") nSku: String,
                    @RequestParam("specialPrice") specialPrice: String,
                    @RequestParam("specialPriceDesc") specialPriceDesc: String,
                    @RequestParam("promotion") promotion: String): String {

        orderService.specials(sku, nSku, specialPrice, specialPriceDesc, promotion)

        return "redirect:/"
    }
}
