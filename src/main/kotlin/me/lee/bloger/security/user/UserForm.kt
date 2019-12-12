package me.lee.bloger.security.user

import java.io.Serializable
import javax.validation.constraints.NotBlank

data class UserForm(@field:NotBlank val username: String,
                    @field:NotBlank val password: String) : Serializable