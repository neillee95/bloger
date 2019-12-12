package me.lee.bloger.system

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class BlogRouterConfig {

    @Bean
    fun blogRouter(blogHandler: BlogHandler) =
            coRouter {
                GET("/system/blog", blogHandler::getBlogConfig)
            }

}