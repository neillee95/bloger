package me.lee.bloger.security

import io.jsonwebtoken.Claims
import me.lee.bloger.security.jwt.Jwt
import me.lee.bloger.security.jwt.JwtProperties
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class BearerTokenServerAuthenticationConverter(private val jwtProperties: JwtProperties) : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange?): Mono<Authentication> {
        return Mono.justOrEmpty(exchange)
                .flatMap {
                    val cookies = it.request.cookies
                    Mono.justOrEmpty(cookies.getFirst(AUTH_TOKEN_COOKIE)?.value)
                }
                .flatMap { Mono.justOrEmpty(Jwt.Verifier.verify(it, jwtProperties.secret)) }
                .map {
                    val principal = claimsToSecurityUser(it)
                    UsernamePasswordAuthenticationToken(principal, "", principal.authorities)
                }
    }


    private fun claimsToSecurityUser(claims: Claims): SecurityUser {
        return SecurityUser(claims[JWT_SUBJECT] as String, "", listOf())
    }

}