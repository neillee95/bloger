package me.lee.bloger.exception

import me.lee.bloger.http.Response
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.function.server.ServerResponse.ok

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleWebExchangeBindException() =
            ok().contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(Response.fail())

}