package me.lee.bloger.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
@EnableConfigurationProperties(JwtProperties::class)
@ConstructorBinding
class JwtProperties(val secret: String,
                    val expireDays: Long = 2)