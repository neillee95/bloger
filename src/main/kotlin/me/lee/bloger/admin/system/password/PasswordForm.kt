package me.lee.bloger.admin.system.password

import java.io.Serializable
import javax.validation.constraints.NotBlank

data class PasswordForm(@field:NotBlank val oldPassword: String,
                        @field:NotBlank val newPassword: String,
                        @field:NotBlank val confirmPassword: String):Serializable