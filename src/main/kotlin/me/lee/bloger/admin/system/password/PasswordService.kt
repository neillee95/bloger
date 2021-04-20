package me.lee.bloger.admin.system.password

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.http.HttpResponse
import me.lee.bloger.http.Response
import me.lee.bloger.security.user.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PasswordService(private val userRepository: UserRepository,
                      private val passwordEncoder: PasswordEncoder) {

    suspend fun updatePassword(passwordForm: PasswordForm): HttpResponse {
        if (passwordForm.newPassword != passwordForm.confirmPassword) {
            return Response.fail()
        }
        return userRepository.findAll()
                .switchIfEmpty(Mono.error(RuntimeException()))
                .last()
                .filter { passwordEncoder.matches(passwordForm.oldPassword, it.password) }
                .map {
                    it.apply {
                        password = passwordEncoder.encode(passwordForm.newPassword)
                    }
                }
                .flatMap {
                    userRepository.save(it)
                            .then(Mono.just(Response.success()))
                }
                .defaultIfEmpty(Response.fail())
                .awaitFirst()
    }

}