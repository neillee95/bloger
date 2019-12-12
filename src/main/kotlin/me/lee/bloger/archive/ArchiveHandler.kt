package me.lee.bloger.archive

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.article.Article
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.text.SimpleDateFormat
import java.util.*

@Component
class ArchiveHandler(private val mongoTemplate: ReactiveMongoTemplate) {

    suspend fun getArchives(serverRequest: ServerRequest): ServerResponse {
        val dateFormat = SimpleDateFormat("yyyy/MM")

        val skip = serverRequest.queryParam("skip").orElse("0").toLong()
        val size = serverRequest.queryParam("size").orElse("5").toInt()

        val query = Query()
                .with(Sort.by(Sort.Direction.DESC, "createTime"))
                .skip(skip)
        query.fields()
                .include("title")
                .include("createTime")

        return mongoTemplate.find(query, Map::class.java, mongoTemplate.getCollectionName(Article::class.java))
                .filter { Objects.nonNull(it["createTime"]) }
                .map {
                    val mutableMap = it.toMutableMap()
                    mutableMap["mappedTime"] = dateFormat.format(it["createTime"])
                    mutableMap
                }
                .groupBy { it["mappedTime"] }
                .flatMap { it.collectList() }
                .limitRate(size)
                .map { Archive(it[0]["mappedTime"] as String, it) }
                .collectList()
                .flatMap { ok().jsonBody(Response.success(it)) }
                .awaitFirst()
    }

}