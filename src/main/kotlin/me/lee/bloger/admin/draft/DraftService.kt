package me.lee.bloger.admin.draft

import kotlinx.coroutines.reactive.awaitSingle
import me.lee.bloger.article.Article
import me.lee.bloger.extension.find
import me.lee.bloger.http.HttpResponse
import me.lee.bloger.http.Response
import org.bson.Document
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.BasicUpdate
import org.springframework.stereotype.Service

@Service
class DraftService(private val mongoTemplate: ReactiveMongoTemplate) {

    suspend fun getDrafts(): HttpResponse {
        val queryDocument = Document("publish", false)
        val selectedFields = arrayOf("title", "tags", "type", "viewCount", "createTime")
        return mongoTemplate.find(queryDocument, selectedFields, Article::class.java, Map::class.java)
                .collectList()
                .map { Response.success(it) }
                .awaitSingle()
    }

    suspend fun publish(articleId: String): HttpResponse {
        val query = BasicQuery(Document("_id", articleId))
        val update = BasicUpdate(Document("\$set", Document("publish", true)))
        return mongoTemplate.updateFirst(query, update, Article::class.java)
                .map {
                    if (it.wasAcknowledged()) Response.success() else Response.fail()
                }
                .awaitSingle()
    }

}