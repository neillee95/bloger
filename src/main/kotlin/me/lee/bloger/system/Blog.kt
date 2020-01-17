package me.lee.bloger.system

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Document("blog")
data class Blog(@Id var id: String? = null,
                @field:NotBlank val name: String,
                val footer: String,
                @field:NotNull val leaveMessage: Boolean,
                val social: List<String> = listOf()) : Serializable