package me.lee.bloger.security.user

import org.bson.types.ObjectId
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component
class UserInitializeService(private val userRepository: UserRepository,
                            private val passwordEncoder: PasswordEncoder) {

    fun userCount() = userRepository.count()

    /**
     * Create the first user with random password.
     */
    fun createFirstUser(): Mono<User> {
        return Mono.defer { Mono.just(generateUser()) }
                .flatMap { userRepository.save(it) }
    }

    /**
     * Generate new user
     */
    fun generateUser(): User {
        val password = UUID.randomUUID().toString().replace("-", "").toLowerCase()
        val id = ObjectId().toHexString()
        val user = User(id, "admin", passwordEncoder.encode(password))
        println("\n\nInitial user: [ username: admin, password: $password ]\n\n")
        return user
    }

}