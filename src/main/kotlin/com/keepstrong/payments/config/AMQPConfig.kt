package com.keepstrong.payments.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AMQPConfig {
    @Bean
    fun createQueue(): Queue {
        return Queue("payment.finished", false)
    }

    @Bean
    fun createRabbitAdmin(connectionFactory: ConnectionFactory): RabbitAdmin {
        return RabbitAdmin(connectionFactory)
    }

    @Bean
    fun initializeAdmin(rabbitAdmin: RabbitAdmin): ApplicationListener<ApplicationReadyEvent> {
        return ApplicationListener { rabbitAdmin.initialize() }
    }
}