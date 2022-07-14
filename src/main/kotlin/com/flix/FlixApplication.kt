package com.flix

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlixApplication

fun main(args: Array<String>) {
    runApplication<FlixApplication>(*args)
}
