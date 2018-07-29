package com.jalgoarena.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Submission(
        val sourceCode: String,
        val userId: String,
        val submissionId: String,
        val problemId: String,
        val token: String,
        val statusCode: String,
        val elapsedTime: Double,
        val submissionTime: String
)
