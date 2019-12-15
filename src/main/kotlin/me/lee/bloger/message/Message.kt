package me.lee.bloger.message

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import javax.validation.constraints.NotBlank

@Document("messages")
data class Message(@Id val id: String? = null,
                   @field:NotBlank val name: String,
                   @field:NotBlank val email: String,
                   @field:NotBlank val website: String,
                   @field:NotBlank val message: String,
                   val time: Long = System.currentTimeMillis(),
                   var show: Boolean = false) : Serializable