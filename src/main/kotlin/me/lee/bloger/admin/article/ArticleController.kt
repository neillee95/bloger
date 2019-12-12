package me.lee.bloger.admin.article

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.mono
import me.lee.bloger.article.Article
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/admin", produces = [MediaType.APPLICATION_JSON_VALUE])
class ArticleController(private val articleService: ArticleService) {

    @PostMapping("/article", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createArticle(@Valid @RequestBody articleMono: Mono<Article>) =
            articleMono.flatMap {
                mono(Dispatchers.Unconfined) {
                    articleService.createArticle(it)
                }
            }

    @PutMapping("/article/{id:\\S+}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateArticle(@PathVariable("id") articleId: String, @Valid @RequestBody articleMono: Mono<Article>) =
            articleMono.flatMap {
                mono(Dispatchers.Unconfined) {
                    articleService.updateArticle(articleId, it)
                }
            }

    @DeleteMapping("/article/{id:\\S+}")
    fun deleteArticle(@PathVariable("id") articleId: String) =
            mono(Dispatchers.Unconfined) {
                articleService.deleteArticle(articleId)
            }

    @GetMapping("/articles")
    fun getArticles() =
            mono(Dispatchers.Unconfined) {
                articleService.getArticles()
            }
}