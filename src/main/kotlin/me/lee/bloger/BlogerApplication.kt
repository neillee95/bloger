package me.lee.bloger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogerApplication

fun main(args: Array<String>) {
    runApplication<BlogerApplication>(*args)
}