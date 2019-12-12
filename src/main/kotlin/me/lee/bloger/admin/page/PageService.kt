package me.lee.bloger.admin.page

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.http.HttpResponse
import me.lee.bloger.http.Response
import me.lee.bloger.page.Page
import me.lee.bloger.page.PageRepository
import me.lee.bloger.page.Pages
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PageService(private val pageRepository: PageRepository) {

    suspend fun updateAboutMe(content: String): HttpResponse =
            updatePage(content, Pages.ABOUTME)

    suspend fun updatePage(content: String, page: Pages): HttpResponse =
            pageRepository.findByName(page.name)
                    .defaultIfEmpty(Page(page.name))
                    .flatMap {
                        it.apply {
                            this.content = content
                        }
                        pageRepository.save(it)
                                .then(Mono.just(Response.success()))
                    }
                    .awaitFirst()

}