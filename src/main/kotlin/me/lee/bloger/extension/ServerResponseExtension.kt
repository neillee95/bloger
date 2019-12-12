package me.lee.bloger.extension

import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import reactor.core.publisher.Mono

fun ServerResponse.BodyBuilder.jsonBody(data: Any): Mono<ServerResponse> =
        this.contentType(MediaType.APPLICATION_JSON).bodyValue(data)

suspend fun ServerResponse.BodyBuilder.jsonBodyAndAwait(data: Any): ServerResponse =
        this.contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(data)