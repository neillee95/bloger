package me.lee.bloger.article

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId
import java.io.Serializable
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Document("articles")
data class Article(@MongoId(FieldType.STRING) var id: String?,
                   @field:NotEmpty var title: String?,
                   @field:NotEmpty var content: String?,
                   val cover: String = "",
                   val tags: List<String> = arrayListOf(),
                   @field:NotEmpty var type: String?,
                   @field:NotNull var canComment: Boolean?,
                   @field:NotNull var publish: Boolean?,
                   var viewCount: Int = 0,
                   var createTime: Long?,
                   var lastModifiedTime: Long?) : Serializable
