package me.lee.bloger.article

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId
import java.io.Serializable
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Document("articles")
data class Article(@MongoId(FieldType.STRING) var id: String,
                   @field:NotEmpty val title: String,
                   @field:NotEmpty val content: String,
                   val cover: String = "",
                   val tags: List<String> = arrayListOf(),
                   @field:NotEmpty val type: String,
                   @field:NotNull val canComment: Boolean,
                   @field:NotNull val publish: Boolean,
                   var viewCount: Int = 0,
                   var createTime: Long,
                   var lastModifiedTime: Long) : Serializable
