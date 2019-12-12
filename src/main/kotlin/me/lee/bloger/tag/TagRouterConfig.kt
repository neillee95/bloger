package me.lee.bloger.tag

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class TagRouterConfig {

    @Bean
    fun tagRouter(tagHandler: TagHandler) =
            coRouter {
                GET("/tags", tagHandler::getTags)
                GET("/tag/{name:.+}", tagHandler::getArticlesByTag)
            }

}