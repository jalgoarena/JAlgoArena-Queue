package com.jalgoarena

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
open class AppConfig {
    @LoadBalanced
    @Bean
    open fun restTemplate() = RestTemplate()
}