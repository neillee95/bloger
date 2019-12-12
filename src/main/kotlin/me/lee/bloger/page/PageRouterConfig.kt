package me.lee.bloger.page

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class PageRouterConfig {

    @Bean
    fun pageRouter(pageHandler: PageHandler) =
            coRouter {
                GET("/aboutme", pageHandler::getAboutMe)
            }

}