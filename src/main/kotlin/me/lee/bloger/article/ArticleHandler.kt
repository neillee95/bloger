package me.lee.bloger.article

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation
import org.springframework.data.mongodb.core.aggregation.Aggregation.project
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.notFound
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.buildAndAwait

@Component
class ArticleHandler(private val mongoTemplate: ReactiveMongoTemplate,
                     private val articleRepository: ArticleRepository) {

    suspend fun getArticles(request: ServerRequest): ServerResponse {

        val aggregation = newAggregation(Article::class.java,
                project("id", "title", "cover", "createTime")
                        .and("content").substring(0, 240))

        return mongoTemplate.aggregate(aggregation, Map::class.java)
                .collectList()
                .flatMap { ok().jsonBody(Response.success(it)) }
                .awaitFirst()
    }

    suspend fun getArticle(request: ServerRequest): ServerResponse =
            articleRepository.findById(request.pathVariable("id"))
                    .flatMap { ok().jsonBody(Response.success(it)) }
                    .defaultIfEmpty(notFound().buildAndAwait())
                    .awaitFirst()

    suspend fun viewArticleAndCount(request: ServerRequest): ServerResponse =
            articleRepository.findById(request.pathVariable("id"))
                    .map {
                        it.apply {
                            viewCount = ++viewCount
                        }
                    }
                    .flatMap { articleRepository.save(it) }
                    .then(ok().jsonBody(Response.success()))
                    .awaitFirst()

}