package me.lee.bloger.security

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono

class JwtReactiveAuthenticationManager(private val userDetailsService: ReactiveUserDetailsService,
                                       private val passwordEncoder: PasswordEncoder) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        return Mono.justOrEmpty(authentication)
                .filter {
                    it.isAuthenticated
                }
                .map { it }
                .switchIfEmpty(Mono.defer { toAuth(authentication) })
    }

    private fun toAuth(authentication: Authentication?): Mono<Authentication> {
        return Mono.justOrEmpty(authentication)
                .cast(UsernamePasswordAuthenticationToken::class.java)
                .flatMap {
                    userDetail(it.name)
                }
                .filter {
                    passwordEncoder.matches(authentication!!.credentials as CharSequence?, it.password)
                }
                .map {
                    UsernamePasswordAuthenticationToken(it, authentication!!.credentials, it.authorities)
                }
    }

    private fun userDetail(username: String?): Mono<UserDetails> =
            if (username != null && SecurityContextHolder.getContext().authentication == null)
                userDetailsService.findByUsername(username)
            else
                Mono.empty()

}