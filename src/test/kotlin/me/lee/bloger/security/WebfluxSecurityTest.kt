package me.lee.bloger.security

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@ExtendWith(SpringExtension::class)
class WebfluxSecurityTest {

    @Autowired
    private lateinit var context: ApplicationContext

    private lateinit var webClient: WebTestClient

    @BeforeEach
    fun construct() {
        webClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .build()
    }

    @Test
    fun securityTest() {
        webClient.get()
                .uri("/admin/articles")
                .exchange()
                .expectStatus().isUnauthorized
    }

}