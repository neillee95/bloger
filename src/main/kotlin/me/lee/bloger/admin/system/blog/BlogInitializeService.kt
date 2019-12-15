package me.lee.bloger.admin.system.blog

import me.lee.bloger.system.Blog
import me.lee.bloger.system.BlogRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class BlogInitializeService(private val blogRepository: BlogRepository) {

    fun blogCount() = blogRepository.count()

    fun createBlogConfig(): Mono<Blog> {
        val blog = Blog(name = "Bloger",
                footer = "One World<br>One Dream",
                leaveMessage = false)
        return blogRepository.save(blog)
    }

}