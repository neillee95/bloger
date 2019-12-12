package me.lee.bloger.tag

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.article.Article
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class TagHandler(private val mongoTemplate: ReactiveMongoTemplate) {

    suspend fun getTags(serverRequest: ServerRequest): ServerResponse =
            mongoTemplate.findDistinct("tags", Article::class.java, String::class.java)
                    .collectList()
                    .flatMap {
                        ok().jsonBody(Response.success(it))
                    }
                    .awaitFirst()

    suspend fun getArticlesByTag(serverRequest: ServerRequest): ServerResponse {
        val query = Query()
                .addCriteria(Criteria.where("tags").`in`(serverRequest.pathVariable("name")))
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