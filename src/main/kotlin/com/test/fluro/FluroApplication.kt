package com.test.fluro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FluroApplication

fun main(args: Array<String>) {
	runApplication<FluroApplication>(*args)
	println("kotlin")
}
