package com.jalgoarena

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
open class JAlgoArenaQueueApp

fun main(args: Array<String>) {
    SpringApplication.run(JAlgoArenaQueueApp::class.java, *args)
}