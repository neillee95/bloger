package me.lee.bloger.article

import me.lee.bloger.http.HttpResponse
import me.lee.bloger.http.HttpResponseCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ArticleRouterTest {

    @Autowired
    lateinit var context: ApplicationContext

    lateinit var webClient: WebTestClient

    @BeforeEach
    fun setUp() {
        webClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .build()
    }

    @Test
    fun getArticlesTest() {
        val httpResponse = webClient.get()
                .uri("/articles")
                .exchange()
                .expectStatus().isOk
                .expectBody(HttpResponse::class.java)
                .returnResult()
                .responseBody
        Assertions.assertNotNull(httpResponse)
        Assertions.assertEquals(httpResponse?.code, HttpResponseCode.SUCCESS)
        Assertions.assertNotNull(httpResponse?.data)
        Assertions.assertTrue(httpResponse?.data is LinkedHashMap<*, *>)
        Assertions.assertEquals((httpResponse?.data as LinkedHashMap<*, *>)["current"], 1)
    }

}