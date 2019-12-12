package me.lee.bloger.page

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface PageRepository : ReactiveMongoRepository<Page, String> {

    fun findByName(name: String): Mono<Page>

}