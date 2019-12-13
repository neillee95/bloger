package me.lee.bloger.archive

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.article.Article
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import org.bson.Document
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class ArchiveHandler(private val mongoTemplate: ReactiveMongoTemplate) {

    /**
     * Get archives with aggregation.
     */
    suspend fun getArchives(serverRequest: ServerRequest): ServerResponse {
        val skip = serverRequest.queryParam("skip").orElse("0").toLong()
        val size = serverRequest.queryParam("size").orElse("5").toLong()

        val aggregation = newAggregation(Article::class.java,
                skip(skip),
                sort(Sort.Direction.DESC, "createTime"),
                project("title", "createTime")
                        .andExpression("toDate(toLong(createTime))")
                        .`as`("timePoint"),
                project("title", "createTime")
                        .and("timePoint")
                        .dateAsFormattedString("%Y/%m")
                        .`as`("timePoint"),
                group("timePoint")
                        .count().`as`("count")
                        .push(Document.parse("{id:'\$_id', title:'\$title', createTime:'\$createTime'}"))
                        .`as`("articles"),
                limit(size)
        )

        return mongoTemplate.aggregate(aggregation, Map::class.java)
                .collectList()
                .flatMap { ok().jsonBody(Response.success(it)) }
                .awaitFirst()
    }

}