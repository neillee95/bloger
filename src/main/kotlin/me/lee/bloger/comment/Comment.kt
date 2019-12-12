package me.lee.bloger.comment

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

//TODO 评论回复
@Document("comment")
data class Comment(@Id val id: String,
                   val commenter: String,
                   val content: String,
                   val commentTime: Long,
                   val article: String): Serializable
