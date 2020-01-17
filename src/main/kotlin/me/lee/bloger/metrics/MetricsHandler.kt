package me.lee.bloger.metrics

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.article.ArticleRepository
import me.lee.bloger.comment.CommentRepository
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import me.lee.bloger.message.MessageRepository
import me.lee.bloger.statistics.VisitorRepository
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class MetricsHandler(private val messageRepository: MessageRepository,
                     private val articleRepository: ArticleRepository,
                     private val visitorRepository: VisitorRepository,
                     private val commentRepository: CommentRepository) {

    suspend fun metrics(serverRequest: ServerRequest): ServerResponse {
        val visitorCountMono = Mono.defer { visitorRepository.count() }
        val messageCountMono = Mono.defer { messageRepository.countByShowIsTrue() }
        val articleCountMono = Mono.defer { articleRepository.countByPublishIsTrue() }
        val commentCountMono = Mono.defer { commentRepository.count() }
        return Mono.zip(visitorCountMono, messageCountMono, articleCountMono, commentCountMono)
                .flatMap {
                    val metrics = Metrics(it.t1, it.t2, it.t3, it.t4)
                    ok().jsonBody(Response.success(metrics))
                }
                .awaitFirst()
    }

}