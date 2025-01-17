package com.sc.authservice.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic expenseAppAuthTopic() {
        return TopicBuilder.name("ExpenseApp-Auth")
            .build();
    }
}