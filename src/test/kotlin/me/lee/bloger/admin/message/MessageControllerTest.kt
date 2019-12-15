package me.lee.bloger.admin.message

import me.lee.bloger.http.HttpResponse
import me.lee.bloger.http.HttpResponseCode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest
@WithMockUser(username = "admin", password = "admin")
class MessageControllerTest {

    @Autowired
    lateinit var context: ApplicationContext

    private lateinit var webClient: WebTestClient

    @BeforeEach
    fun setup() {
        webClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .build()
    }

    @Test
    fun getLeaveMessageTest() {
        val httpResponse = webClient.get()
                .uri("/admin/messages")
                .exchange()
                .expectStatus().isOk
                .expectBody(HttpResponse::class.java)
                .returnResult()
                .responseBody

        Assertions.assertEquals(httpResponse?.code, HttpResponseCode.SUCCESS)
        Assertions.assertNotNull(httpResponse?.code)
        Assertions.assertTrue(httpResponse?.data is LinkedHashMap<*, *>)
        Assertions.assertEquals((httpResponse?.data as LinkedHashMap<*, *>)["current"], 1)
    }

}