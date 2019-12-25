package me.lee.bloger.metrics

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class MetricsRouterConfig {

    @Bean
    fun metricsRouter(metricsHandler: MetricsHandler) =
            coRouter {
                GET("/metrics", metricsHandler::metrics)
            }

}