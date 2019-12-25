package me.lee.bloger.security

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.extension.jsonBody
import me.lee.bloger.http.Response
import me.lee.bloger.security.jwt.Jwt
import me.lee.bloger.security.jwt.JwtProperties
import me.lee.bloger.security.user.UserForm
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.time.Duration
import javax.validation.Validator

@Component
class AuthHandler(private val validator: Validator,
                  private val jwtProperties: JwtProperties,
                  private val authenticationManager: ReactiveAuthenticationManager) {

    suspend fun auth(request: ServerRequest): ServerResponse {
        return request.bodyToMono(UserForm::class.java)
                .filter { validator.validate(it).isEmpty() }
                .flatMap { form ->
                    val authenticationToken = UsernamePasswordAuthenticationToken(form.username, form.password)
                    authenticationManager.authenticate(authenticationToken)
                            .flatMap { response(it.principal as SecurityUser) }
                            .switchIfEmpty(ok().jsonBody(Response.fail()))
                            .onErrorResume { ok().jsonBody(Response.fail())}
                }
                .switchIfEmpty(ok().jsonBody(Response.fail()))
                .awaitFirst()
    }

    private fun response(user: SecurityUser): Mono<ServerResponse> {
        val claims = HashMap<String, Any>(2)
        claims[JWT_SUBJECT] = user.username

        val authTokenCookie = ResponseCookie
                .from(AUTH_TOKEN_COOKIE, Jwt.Signer.sign(jwtProperties.secret, claims, jwtProperties.expireDays))
                .maxAge(Duration.ofDays(jwtProperties.expireDays))
                .path("/")
                .build()
        return ok().contentType(MediaType.APPLICATION_JSON)
                .cookie(authTokenCookie)
                .bodyValue(Response.success())
    }

}