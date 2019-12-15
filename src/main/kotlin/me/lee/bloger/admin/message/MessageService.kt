package me.lee.bloger.admin.message

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.exception.ObjectNotFoundException
import me.lee.bloger.http.HttpResponse
import me.lee.bloger.http.PageResult
import me.lee.bloger.http.Response
import me.lee.bloger.message.Message
import me.lee.bloger.message.MessageRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MessageService(private val mongoTemplate: ReactiveMongoTemplate,
                     private val messageRepository: MessageRepository) {

    suspend fun getLeaveMessages(page: Int, size: Int): HttpResponse {
        val findMono = Mono.defer {
            val query = Query()
                    .with(PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "time")))
            mongoTemplate.find(query, Message::class.java)
                    .collectList()
        }

        val countMono = Mono.defer {
            messageRepository.count()
        }

        return Mono.zip(findMono, countMono)
                .map { Response.success(PageResult(page, it.t2, it.t1)) }
                .awaitFirst()
    }

    suspend fun switchShowState(messageId: String): HttpResponse {
        return messageRepository.findById(messageId)
                .switchIfEmpty(Mono.error(ObjectNotFoundException()))
                .flatMap {
                    it.apply {
                        this.show = !this.show
                    }
                    messageRepository.save(it)
                            .then(Mono.just(Response.success()))
                }
                .awaitFirst()
    }

}