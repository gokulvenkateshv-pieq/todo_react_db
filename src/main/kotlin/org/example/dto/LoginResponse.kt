package org.example.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class LoginResponse @JsonCreator constructor(
    @JsonProperty("success") val success: Boolean,
    @JsonProperty("message") val message: String,
    @JsonProperty("username") val username: String? = null
)
