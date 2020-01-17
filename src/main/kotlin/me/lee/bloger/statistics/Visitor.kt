package me.lee.bloger.statistics

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.*
import javax.validation.constraints.NotEmpty

@Document("visitors")
data class Visitor(@Id val id: String? = null,
                   val time: Date = Date(),
                   @field:NotEmpty val country: String,
                   @field:NotEmpty val browser: String) : Serializable