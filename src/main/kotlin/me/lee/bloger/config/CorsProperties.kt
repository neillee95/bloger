package me.lee.bloger.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "cors")
class CorsProperties(var credentials: Boolean? = true,
                     var headers: List<String>? = listOf("*"),
                     var origins: List<String>? = listOf("*"),
                     var methods: List<String>? = listOf("*"),
                     var age: Long? = 18_000L)