package me.lee.bloger.security

import me.lee.bloger.security.jwt.JwtProperties
import me.lee.bloger.security.user.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(userRepository: UserRepository): ReactiveUserDetailsService =
            SecurityUserDetailsService(userRepository)

    @Bean
    fun authenticationManager(userDetailsService: ReactiveUserDetailsService,
                              passwordEncoder: PasswordEncoder): ReactiveAuthenticationManager =
            JwtReactiveAuthenticationManager(userDetailsService, passwordEncoder)

    fun authenticationWebFilter(jwtProperties: JwtProperties,
                                authenticationManager: ReactiveAuthenticationManager): AuthenticationWebFilter {
        val authenticationWebFilter = AuthenticationWebFilter(authenticationManager)
        val bearerAuthenticationConverter = BearerTokenServerAuthenticationConverter(jwtProperties)
        authenticationWebFilter.setServerAuthenticationConverter(bearerAuthenticationConverter)
        authenticationWebFilter.setRequiresAuthenticationMatcher(JwtCookiesExchangeMatcher())
        return authenticationWebFilter
    }

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity,
                               jwtProperties: JwtProperties,
                               authenticationManager: ReactiveAuthenticationManager): SecurityWebFilterChain =
            http.cors().and()
                    .csrf().disable()
                    .httpBasic().disable()
                    .formLogin().disable()
                    .logout().disable()
                    .exceptionHandling()
                        .authenticationEntryPoint(ExceptionHandler.UnauthorizedAuthenticationEntryPoint())
                        .accessDeniedHandler(ExceptionHandler.AccessDeniedHandler())
                    .and()
                    .authorizeExchange()
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers("/admin/**").authenticated()
                        .anyExchange().permitAll()
                    .and()
                        .addFilterAt(authenticationWebFilter(jwtProperties, authenticationManager),
                                SecurityWebFiltersOrder.AUTHENTICATION)
                    .build()

}