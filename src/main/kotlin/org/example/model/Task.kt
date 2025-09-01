package org.example.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor

data class Task @JdbiConstructor constructor(
    @JsonProperty("id") val id: Int? = null,
    @JsonProperty("text") val text: String,
    @JsonProperty("description") val description: String? = null,
    @JsonProperty("completed") val completed: Boolean = false
)
