package me.lee.bloger.page

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId

@Document("page")
data class Page(@MongoId val name: String,
                var content: String = "")