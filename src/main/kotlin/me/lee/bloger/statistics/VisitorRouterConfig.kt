package me.lee.bloger.statistics

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class VisitorRouterConfig {

    @Bean
    fun visitorRouter(visitorHandler: VisitorHandler) =
            coRouter {
                POST("/statistics/new/visitors", visitorHandler::newVisitors)
            }

}