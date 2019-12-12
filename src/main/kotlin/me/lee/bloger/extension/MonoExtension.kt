package me.lee.bloger.extension

import reactor.core.publisher.Mono

fun <T> T.toMono(): Mono<T> = Mono.just(this!!)