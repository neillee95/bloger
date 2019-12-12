package me.lee.bloger.security

import me.lee.bloger.security.user.UserRepository
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import reactor.core.publisher.Mono

class SecurityUserDetailsService(private val userRepository: UserRepository) : ReactiveUserDetailsService {

    override fun findByUsername(username: String): Mono<UserDetails> =
            userRepository.findByUsername(username)
                    .switchIfEmpty(Mono.error(UsernameNotFoundException("username or password is not valid.")))
                    .map {
                        SecurityUserFactory.create(it)
                    }

}