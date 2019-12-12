package me.lee.bloger

import me.lee.bloger.security.user.UserService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class ApplicationInitializer(private val userService: UserService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        userService.userCount()
                .filter { it.toInt() == 0 }
                .flatMap { userService.createFirstUser() }
                .subscribe()
    }

}