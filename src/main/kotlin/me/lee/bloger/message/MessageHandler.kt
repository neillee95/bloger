package me.lee.bloger.message

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import javax.validation.Validator

@Component
class MessageHandler(private val messageRepository: MessageRepository,
                     private val mongoTemplate: ReactiveMongoTemplate,
                     private val validator: Validator) {

    suspend fun leaveMessage(serverRequest: ServerRequest): ServerResponse {
        return serverRequest.bodyToMono(Message::class.java)
                .filter { validator.validate(it).isEmpty() }
                .flatMap {
                    messageRepository.save(it)
                            .then(ok().jsonBody(Response.success()))
                }
                .switchIfEmpty(ok().jsonBody(Response.fail()))
                .awaitFirst()
    }

    suspend fun getLeaveMessages(serverRequest: ServerRequest): ServerResponse {
        val query = Query()
                .with(Sort.by(Sort.Direction.DESC, "time"))
                .addCriteria(Criteria.where("show").`is`(true))
        query.fields()
                .exclude("_id")
                .include("name")
                .include("time")
                .include("message")

        return mongoTemplate.find(query, Map::class.java, mongoTemplate.getCollectionName(Message::class.java))
                .collectList()
                .flatMap { ok().jsonBody(Response.success(it)) }
                .awaitFirst()
    }

}