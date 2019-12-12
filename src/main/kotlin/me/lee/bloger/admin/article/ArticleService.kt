package me.lee.bloger.admin.article

import kotlinx.coroutines.reactive.awaitSingle
import me.lee.bloger.article.Article
import me.lee.bloger.article.ArticleRepository
import me.lee.bloger.exception.ObjectNotFoundException
import me.lee.bloger.extension.find
import me.lee.bloger.http.HttpResponse
import me.lee.bloger.http.Response
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ArticleService(private val articleRepository: ArticleRepository,
                     private val mongoTemplate: ReactiveMongoTemplate) {

    suspend fun createArticle(article: Article): HttpResponse {
        article.apply {
            id = ObjectId().toHexString()
            val now = System.currentTimeMillis()
            createTime = now
            lastModifiedTime = now
        }
        return articleRepository.save(article)
                .then(Mono.just(Response.success()))
                .awaitSingle()
    }

    suspend fun updateArticle(articleId: String, article: Article): HttpResponse =
            articleRepository.findById(articleId)
                    .flatMap {
                        article.apply {
                            id = articleId
                            lastModifiedTime = System.currentTimeMillis()
                        }
                        articleRepository.save(article)
                                .then(Mono.just(Response.success()))
                    }
                    .switchIfEmpty(Mono.error(ObjectNotFoundException()))
                    .awaitSingle()


    suspend fun deleteArticle(articleId: String): HttpResponse =
            articleRepository.findById(articleId)
                    .flatMap {
                        articleRepository.delete(it)
                                .then(Mono.just(Response.success()))
                    }
                    .switchIfEmpty(Mono.error(ObjectNotFoundException()))
                    .awaitSingle()

    suspend fun getArticles(): HttpResponse {
        return mongoTemplate
                .find(selectedFields = arrayOf("title", "tags", "type", "viewCount", "createTime"),
                        entityClass = Article::class.java,
                        resultClass = Map::class.java)
                .collectList()
                .map { Response.success(it) }
                .awaitSingle()
    }

}