package me.lee.bloger.security

import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class ExceptionHandler {

    class UnauthorizedAuthenticationEntryPoint : ServerAuthenticationEntryPoint {

        override fun commence(exchange: ServerWebExchange, e: AuthenticationException?): Mono<Void> {
            return Mono.fromRunnable { exchange.response.statusCode = HttpStatus.UNAUTHORIZED }
        }

    }

    class AccessDeniedHandler : ServerAccessDeniedHandler {

        override fun handle(exchange: ServerWebExchange, denied: AccessDeniedException?): Mono<Void> {
            return Mono.fromRunnable { exchange.response.statusCode = HttpStatus.FORBIDDEN }
        }

    }

}