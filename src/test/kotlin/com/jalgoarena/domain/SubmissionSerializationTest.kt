package com.jalgoarena.domain

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.intellij.lang.annotations.Language
import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.json.JacksonTester
import java.time.LocalDateTime
import java.time.Month
import java.util.*


class SubmissionSerializationTest {

    private lateinit var json: JacksonTester<Submission>

    @Before
    fun setup() {
        val objectMapper = jacksonObjectMapper()
        JacksonTester.initFields(this, objectMapper)
    }

    @Test
    fun should_serialize_submission() {
        assertThat(json.write(SUBMISSION))
                .isEqualToJson("submission.json")
    }

    @Test
    fun should_deserialize_submission() {
        assertThat(json.parse(SUBMISSION_JSON))
                .isEqualTo(SUBMISSION)
    }

    companion object {
        private val uuid = UUID.fromString("e0c0f9e7-a21c-4dba-ab22-b3fcca695b50")
        private val date = LocalDateTime.of(2018, Month.JULY, 29, 6, 48)

        private val SUBMISSION = Submission(
                problemId = "fib",
                elapsedTime = -1.0,
                sourceCode = "dummy source code",
                statusCode = "WAITING",
                userId = "0-0",
                submissionId = uuid.toString(),
                submissionTime = date.toString(),
                token = "dummy_token"
        )

        @Language("JSON")
        private val SUBMISSION_JSON = """{
  "problemId": "fib",
  "elapsedTime": -1.0,
  "sourceCode": "dummy source code",
  "statusCode": "WAITING",
  "userId": "0-0",
  "submissionId": "e0c0f9e7-a21c-4dba-ab22-b3fcca695b50",
  "submissionTime": "2018-07-29T06:48",
  "token": "dummy_token"
}
"""
    }
}