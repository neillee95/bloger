package me.lee.bloger.system

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class BlogHandler(private val blogRepository: BlogRepository) {

    suspend fun getBlogConfig(serverRequest: ServerRequest): ServerResponse =
            blogRepository.findAll()
                    .defaultIfEmpty(Blog(""))
                    .last()
                    .flatMap { ok().jsonBody(Response.success(it)) }
                    .awaitFirst()

}