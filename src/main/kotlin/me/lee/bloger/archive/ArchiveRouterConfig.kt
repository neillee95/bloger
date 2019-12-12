package me.lee.bloger.archive

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ArchiveRouterConfig {

    @Bean
    fun archiveRouter(archiveHandler: ArchiveHandler) = coRouter {
        GET("/archives", archiveHandler::getArchives)
    }

}