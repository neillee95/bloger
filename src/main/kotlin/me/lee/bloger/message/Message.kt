package me.lee.bloger.message

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.io.Serializable
import javax.validation.constraints.NotBlank

@Document("messages")
data class Message(@MongoId val id: String? = null,
                   @field:NotBlank val name: String,
                   @field:NotBlank val email: String,
                   @field:NotBlank val website: String,
                   @field:NotBlank val message: String,
                   val time: Long = System.currentTimeMillis(),
                   val show: Boolean = false) : Serializable