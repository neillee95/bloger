package me.lee.bloger.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class AuthRouterConfig {

    @Bean
    fun authRouter(authHandler: AuthHandler) = coRouter {
        POST("/auth", authHandler::auth)
    }

}