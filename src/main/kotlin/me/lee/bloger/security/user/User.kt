package me.lee.bloger.security.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class User(@Id val id: String,
                val username: String,
                var password: String,
                val avatar: String = "")