package me.lee.bloger.admin.system.password

import kotlinx.coroutines.reactor.mono
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/admin/system", produces = [MediaType.APPLICATION_JSON_VALUE])
class PasswordController(private val passwordService: PasswordService) {

    @PutMapping("/password", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updatePassword(@Valid @RequestBody passwordForm: Mono<PasswordForm>) =
            passwordForm.flatMap {
                mono {
                    passwordService.updatePassword(it)
                }
            }

}