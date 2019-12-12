package me.lee.bloger.system

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Document("blog")
data class Blog(@MongoId var id: String = "",
                @field:NotBlank val name: String = "",
                val footer: String = "",
                @field:NotNull val leaveMessage: Boolean = false) : Serializable