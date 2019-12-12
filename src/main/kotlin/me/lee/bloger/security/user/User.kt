package me.lee.bloger.security.user

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "users")
data class User(@MongoId val id: String,
                val username: String,
                var password: String,
                val avatar: String = "")