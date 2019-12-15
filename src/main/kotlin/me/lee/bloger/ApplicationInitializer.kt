package me.lee.bloger

import me.lee.bloger.admin.system.blog.BlogInitializeService
import me.lee.bloger.security.user.UserInitializeService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class ApplicationInitializer(private val userInitializeService: UserInitializeService,
                             private val blogInitializeService: BlogInitializeService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        userInitializeService.userCount()
                .filter { it.toInt() == 0 }
                .flatMap { userInitializeService.createFirstUser() }
                .subscribe()

        blogInitializeService.blogCount()
                .filter {it.toInt() == 0}
                .flatMap { blogInitializeService.createBlogConfig() }
                .subscribe()
    }

}