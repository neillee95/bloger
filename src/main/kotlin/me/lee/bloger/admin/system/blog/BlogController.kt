package me.lee.bloger.admin.system.blog

import kotlinx.coroutines.reactor.mono
import me.lee.bloger.admin.system.blog.BlogService
import me.lee.bloger.system.Blog
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/admin/system")
class BlogController(private val blogService: BlogService) {

    @PutMapping("/blog", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveBlogConfig(@RequestBody blogMono: Mono<Blog>) =
            blogMono.flatMap {
                mono {
                    blogService.saveBlogConfig(it)
                }
            }

}