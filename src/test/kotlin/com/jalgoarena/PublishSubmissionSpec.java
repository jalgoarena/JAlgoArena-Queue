package com.jalgoarena;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jalgoarena.domain.JudgeRequest;
import com.jalgoarena.domain.Submission;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class PublishSubmissionSpec {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublishSubmissionSpec.class);

    private static final String SENDER_TOPIC = "submissions";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JudgeRequest fibJudgeRequest = new JudgeRequest("source code", "user id", "java");

    @Autowired
    private QueueController queueController;

    private KafkaMessageListenerContainer<String, Submission> container;

    private BlockingQueue<ConsumerRecord<String, String>> records;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(
            1, true, 1,SENDER_TOPIC
    );

    @Before
    public void setUp() throws Exception {
        Map<String, Object> consumerProperties =
                KafkaTestUtils.consumerProps("sender", "false", embeddedKafka);

        DefaultKafkaConsumerFactory<String, Submission> consumerFactory =
                new DefaultKafkaConsumerFactory<>(consumerProperties);

        ContainerProperties containerProperties = new ContainerProperties(SENDER_TOPIC);

        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

        records = new LinkedBlockingQueue<>();

        container.setupMessageListener((MessageListener<String, String>) record -> {
            LOGGER.info("test-listener received message='{}'", record.toString());
            records.add(record);
        });

        container.start();

        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
    }

    @After
    public void tearDown() {
        container.stop();
    }

    @Test
    public void publish_message() throws Exception {
        queueController.publish(
                "fib",
                fibJudgeRequest,
                "dummy_token"
        );

        ConsumerRecord<String, String> received = records.poll(10, TimeUnit.SECONDS);

        String submission = received.value();
        assertThat(submission).contains("fib");
        assertThat(submission).contains("dummy_token");

        JsonNode jsonNode = OBJECT_MAPPER.readTree(submission);

        assertThat(jsonNode.get("submissionId")).isNotNull();
    }
}
