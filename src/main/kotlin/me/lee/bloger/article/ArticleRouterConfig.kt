package me.lee.bloger.article

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class ArticleRouterConfig {

    @Bean
    fun articleRouter(articleHandler: ArticleHandler) =
            coRouter {
                GET("/articles", articleHandler::getArticles)
                GET("/article/{id:\\S+}", articleHandler::getArticle)
                PUT("/article/{id:\\S+}/view", articleHandler::viewArticleAndCount)
            }

}