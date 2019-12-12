package me.lee.bloger.admin.page

import kotlinx.coroutines.reactor.mono
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin", produces = [MediaType.APPLICATION_JSON_VALUE])
class PageController(private val pageService: PageService) {

    @PutMapping("/aboutme", consumes = [MediaType.TEXT_PLAIN_VALUE])
    fun getAboutMe(@RequestBody content: String) =
            mono {
                pageService.updateAboutMe(content)
            }

}