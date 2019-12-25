package me.lee.bloger.exception

import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@RestControllerAdvice
class GlobalExceptionHandler {

    val log: Logger by lazy {
        LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleWebExchangeBindException(e: WebExchangeBindException): Mono<ServerResponse> {
        log.debug("Bad request", e)
        return ok().jsonBody(Response.fail())
    }

}