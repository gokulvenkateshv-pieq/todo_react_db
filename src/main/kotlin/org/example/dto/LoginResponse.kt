
package org.example.dto

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val username: String? = null
)
