package me.lee.bloger.system

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class BlogHandler(private val blogRepository: BlogRepository) {

    suspend fun getBlogConfig(serverRequest: ServerRequest): ServerResponse =
            blogRepository.findAll()
                    .switchIfEmpty(Mono.error(Exception("Blog config is not exist, restart to create the initial config.")))
                    .last()
                    .flatMap { ok().jsonBody(Response.success(it)) }
                    .awaitFirst()

}