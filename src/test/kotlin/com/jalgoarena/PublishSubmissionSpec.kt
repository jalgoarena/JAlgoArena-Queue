package com.jalgoarena

import com.fasterxml.jackson.databind.ObjectMapper
import com.jalgoarena.domain.JudgeRequest
import com.jalgoarena.domain.User
import com.jalgoarena.web.UsersClient
import com.nhaarman.mockito_kotlin.whenever
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.kafka.test.rule.KafkaEmbedded
import org.springframework.kafka.test.utils.KafkaTestUtils
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = ["submissions"])
class PublishSubmissionSpec {

    private val fibJudgeRequest = JudgeRequest("source code", USER_ID, "java")

    @Autowired
    private lateinit var queueController: QueueController

    @Autowired
    private lateinit var embeddedKafka: KafkaEmbedded

    @MockBean
    private lateinit var usersClient: UsersClient

    @Test
    fun publish_submission_message() {
        val consumerProperties = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafka)
        consumerProperties[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"

        val consumerFactory = DefaultKafkaConsumerFactory<Int, String>(consumerProperties)

        publish_message()

        val consumer = consumerFactory.createConsumer()
        embeddedKafka.consumeFromAnEmbeddedTopic(consumer, SUBMISSIONS_TOPIC)
        val replies = KafkaTestUtils.getRecords(consumer)

        val submission = replies.first()!!.value()
        assertThat(submission).contains("fib")
        assertThat(submission).contains("dummy_token")

        val jsonNode = OBJECT_MAPPER.readTree(submission)

        assertThat(jsonNode.get("submissionId")).isNotNull()
    }


    private fun publish_message() {
        setupUserAuth()

        val response = queueController.publish(
                "fib",
                fibJudgeRequest,
                DUMMY_TOKEN
        )

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    private fun setupUserAuth() {
        val user = User("dummy", "dummy", "dummy", "dummy", USER_ID)
        whenever(usersClient.findUser(DUMMY_TOKEN)).thenReturn(user)
    }

    companion object {

        private const val SUBMISSIONS_TOPIC = "submissions"

        private val OBJECT_MAPPER = ObjectMapper()
        private const val DUMMY_TOKEN = "dummy_token"
        private const val USER_ID = "user id"
    }
}
