package me.lee.bloger.article

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : ReactiveMongoRepository<Article, String>
