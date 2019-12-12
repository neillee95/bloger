package me.lee.bloger.security

import me.lee.bloger.security.user.User

class SecurityUserFactory {

    companion object {

        fun create(user: User): SecurityUser =
                SecurityUser(user.username, user.password, mutableListOf())

    }

}