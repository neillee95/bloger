package me.lee.bloger.security

import me.lee.bloger.extension.toMono
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class JwtCookiesExchangeMatcher : ServerWebExchangeMatcher {

    override fun matches(exchange: ServerWebExchange): Mono<ServerWebExchangeMatcher.MatchResult> {
        return exchange.request.cookies.toMono()
                .filter {
                    it.containsKey(AUTH_TOKEN_COOKIE)
                }
                .flatMap { ServerWebExchangeMatcher.MatchResult.match() }
                .switchIfEmpty(ServerWebExchangeMatcher.MatchResult.notMatch())
    }

}