package me.lee.bloger.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

@Configuration
class GlobalWebConfig {

    private fun corsConfiguration(corsProperties: CorsProperties): CorsConfiguration {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowCredentials = corsProperties.credentials
        corsConfiguration.allowedHeaders = corsProperties.headers
        corsConfiguration.allowedOrigins = corsProperties.origins
        corsConfiguration.allowedMethods = corsProperties.methods
        corsConfiguration.maxAge = corsProperties.age
        return corsConfiguration
    }

    @Bean
    fun corsConfigurationSource(corsProperties: CorsProperties): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration(corsProperties))
        return source
    }

//    @Bean
//    fun corsWebFilter(corsProperties: CorsProperties): CorsWebFilter {
//        return CorsWebFilter(corsConfigurationSource(corsProperties))
//    }

}