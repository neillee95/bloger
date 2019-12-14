package me.lee.bloger.article

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface ArticleRepository : ReactiveMongoRepository<Article, String> {

    fun countByPublishIs(publish: Boolean): Mono<Long>

}