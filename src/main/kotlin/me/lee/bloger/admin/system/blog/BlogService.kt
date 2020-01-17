package me.lee.bloger.admin.system.blog

import kotlinx.coroutines.reactive.awaitFirst
import me.lee.bloger.extension.toMono
import me.lee.bloger.http.HttpResponse
import me.lee.bloger.http.Response
import me.lee.bloger.system.Blog
import me.lee.bloger.system.BlogRepository
import org.springframework.stereotype.Service

@Service
class BlogService(private val blogRepository: BlogRepository) {

    suspend fun saveBlogConfig(blog: Blog): HttpResponse {
        return blogRepository.findAll()
                .next()
                .map {
                    blog.id = it.id
                    blog
                }
                .flatMap { blogRepository.save(it) }
                .then(Response.success().toMono())
                .awaitFirst()
    }

}