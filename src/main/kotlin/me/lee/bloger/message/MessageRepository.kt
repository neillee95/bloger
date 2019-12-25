package me.lee.bloger.message

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface MessageRepository : ReactiveMongoRepository<Message, String> {

    fun countByShowIsTrue(): Mono<Long>

}