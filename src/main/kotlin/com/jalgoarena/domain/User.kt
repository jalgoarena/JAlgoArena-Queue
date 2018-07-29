package com.jalgoarena.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
        val username: String,
        val region: String,
        val team: String,
        val role: String,
        var id: String
)