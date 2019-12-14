package me.lee.bloger.article

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.extension.pageParam
import me.lee.bloger.http.PageResult
import me.lee.bloger.http.Response
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.notFound
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.buildAndAwait
import reactor.core.publisher.Mono

@Component
class ArticleHandler(private val mongoTemplate: ReactiveMongoTemplate,
                     private val articleRepository: ArticleRepository) {

    suspend fun getArticles(request: ServerRequest): ServerResponse {
        val pagination = request.pageParam(defaultSize = 20)

        val aggregation = newAggregation(Article::class.java,
                match(Criteria.where("publish").`is`(true)),
                skip((pagination.page - 1) * pagination.size),
                project("id", "title", "cover", "createTime")
                        .and("content").substring(0, 240),
                limit(pagination.size),
                sort(Sort.Direction.DESC, "createTime")
        )

        val aggregationMono = Mono.defer {
            mongoTemplate.aggregate(aggregation, Map::class.java)
                    .collectList()
        }

        val countMono = Mono.defer {
            articleRepository.countByPublishIs(true)
        }

        return Mono.zip(aggregationMono, countMono)
                .flatMap {
                    val pageResult = PageResult(pagination.page, it.t2, it.t1)
                    ok().jsonBody(Response.success(pageResult))
                }
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