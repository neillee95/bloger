package me.lee.bloger.message

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import javax.validation.Validator

@Component
class MessageHandler(private val messageRepository: MessageRepository,
                     private val validator: Validator) {

    suspend fun leaveMessage(serverRequest: ServerRequest): ServerResponse {
        return serverRequest.bodyToMono(Message::class.java)
                .filter { validator.validate(it).isEmpty() }
                .flatMap {
                    messageRepository.save(it)
                            .then(ok().jsonBody(Response.success()))
                }
                .switchIfEmpty(ok().jsonBody(Response.fail()))
                .awaitFirst()
    }

}