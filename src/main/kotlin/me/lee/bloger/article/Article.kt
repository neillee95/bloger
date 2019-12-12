package me.lee.bloger.article

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId
import java.io.Serializable
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * 文章
 * id                  文章id
 * title               文章标题
 * content             文章内容
 * cover               封面图
 * tags                标签
 * type                类型
 * canComment          是否可以评论
 * publish             是否发表
 * viewCount           查看次数
 * createTime          创建时间
 * lastModifiedTime    最近修改时间
 */
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
