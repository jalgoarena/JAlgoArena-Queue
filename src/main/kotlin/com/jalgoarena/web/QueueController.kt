package com.jalgoarena.web

import com.jalgoarena.domain.JudgeRequest
import com.jalgoarena.domain.Submission
import com.jalgoarena.domain.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFutureCallback
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*

@RestController
class QueueController(
        @Autowired private val usersClient: UsersClient
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    private lateinit var template: KafkaTemplate<Int, Submission>

    @PostMapping("/problems/{problemId}/publish", produces = ["application/json"])
    fun publish(
            @PathVariable problemId: String,
            @RequestBody judgeRequest: JudgeRequest,
            @RequestHeader("X-Authorization", required = false) token: String?
    ) = checkUser(token) { user ->
        when {
            user.id != judgeRequest.userId || token == null -> unauthorized()
            else -> {
                val submission = Submission(
                        sourceCode = judgeRequest.sourceCode,
                        userId = judgeRequest.userId,
                        problemId = problemId,
                        submissionId = UUID.randomUUID().toString(),
                        token = token,
                        statusCode = "WAITING",
                        elapsedTime = -1.0,
                        submissionTime = LocalDateTime.now().toString()

                )

                logger.info("Publishing submission [submissionId={}]", submission.submissionId)

                val future = template.send("submissions", submission)
                future.addCallback(PublishHandler(submission.submissionId.toString()))

                ok(submission)
            }
        }
    }

    private fun <T> checkUser(token: String?, action: (User) -> ResponseEntity<T>): ResponseEntity<T> {
        if (token == null) {
            return unauthorized()
        }

        val user = usersClient.findUser(token) ?: return unauthorized()
        return action(user)
    }

    private fun <T> unauthorized(): ResponseEntity<T> = ResponseEntity(HttpStatus.UNAUTHORIZED)

    class PublishHandler(
            private val submissionId: String?
    ) : ListenableFutureCallback<SendResult<Int, Submission>> {

        private val logger = LoggerFactory.getLogger(this.javaClass)

        override fun onSuccess(result: SendResult<Int, Submission>?) {
            logger.info("Published successfully submission [submissionId={}]", submissionId)
        }

        override fun onFailure(ex: Throwable?) {
            logger.error("Error during publishing submission [submissionId={}]", submissionId, ex)
        }

    }
}
