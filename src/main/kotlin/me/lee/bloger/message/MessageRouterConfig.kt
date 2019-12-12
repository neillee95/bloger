package me.lee.bloger.message

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MessageRouterConfig {

    @Bean
    fun messageRouter(messageHandler: MessageHandler) =
            coRouter {
                POST("/message",
                        RequestPredicates.contentType(MediaType.APPLICATION_JSON),
                        messageHandler::leaveMessage)
            }

}