package me.lee.bloger.admin.draft

import kotlinx.coroutines.reactor.mono
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin", produces = [MediaType.APPLICATION_JSON_VALUE])
class DraftController(private val draftService: DraftService) {

    @GetMapping("/drafts")
    fun getDrafts() =
            mono {
                draftService.getDrafts()
            }

    @PutMapping("/draft/{articleId:\\S+}/publish")
    fun publish(@PathVariable("articleId") articleId: String) =
            mono {
                draftService.publish(articleId)
            }

}