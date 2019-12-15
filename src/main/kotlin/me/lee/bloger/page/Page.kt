package me.lee.bloger.page

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("page")
data class Page(@Id val name: String,
                var content: String = "")