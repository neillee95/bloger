package me.lee.bloger.page

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.extension.jsonBodyAndAwait
import me.lee.bloger.http.Response
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class PageHandler(private val pageRepository: PageRepository) {

    suspend fun getAboutMe(serverRequest: ServerRequest): ServerResponse =
            pageRepository.findByName(Pages.ABOUTME.name)
                    .flatMap {
                        ok().jsonBody(Response.success(it.content))
                    }
                    .defaultIfEmpty(ok().jsonBodyAndAwait(Response.success()))
                    .awaitFirst()

}