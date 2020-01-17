package me.lee.bloger.statistics

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import javax.validation.Validator

@Component
class VisitorHandler(private val visitorRepository: VisitorRepository,
                     private val validator: Validator) {

    suspend fun newVisitors(serverRequest: ServerRequest): ServerResponse {
        return serverRequest.bodyToMono(Visitor::class.java)
                .filter { validator.validate(it).isEmpty() }
                .flatMap {
                    visitorRepository.save(it)
                            .then(ok().jsonBody(Response.success()))
                }
                .switchIfEmpty(ok().jsonBody(Response.fail()))
                .awaitFirst()
    }

}