package me.lee.bloger.tag

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.article.Article
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class TagHandler(private val mongoTemplate: ReactiveMongoTemplate) {

    suspend fun getTags(serverRequest: ServerRequest): ServerResponse {
        val aggregation = newAggregation(Article::class.java,
                match(Criteria.where("publish").`is`(true)),
                unwind("tags"),
                group("tags")
                        .count().`as`("count"))
        return mongoTemplate.aggregate(aggregation, Map::class.java)
                .collectList()
                .flatMap { ok().jsonBody(Response.success(it)) }
                .awaitFirst()
    }

    suspend fun getArticlesByTag(serverRequest: ServerRequest): ServerResponse {
        val criteria = Criteria.where("publish").`is`(true)
                        .and("tags").`in`(serverRequest.pathVariable("name"))
        val query = Query(criteria)
        query.fields()
                .include("title")
        return mongoTemplate.find(query, Map::class.java, mongoTemplate.getCollectionName(Article::class.java))
                .collectList()
                .flatMap {
                    ok().jsonBody(Response.success(it))
                }
                .awaitFirst()
    }

}