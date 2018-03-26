package com.jalgoarena

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class QueueApp

fun main(args: Array<String>) {
    SpringApplication.run(QueueApp::class.java, *args)
}