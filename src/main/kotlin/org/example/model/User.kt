package org.example.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class User @JsonCreator constructor(
    @JsonProperty("id") val id: Int,
    @JsonProperty("username") val username: String,
    @JsonProperty("password") val password: String
)
