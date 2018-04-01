package com.jalgoarena

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableEurekaClient
@EnableKafka
open class QueueApp

fun main(args: Array<String>) {
    SpringApplication.run(QueueApp::class.java, *args)
}