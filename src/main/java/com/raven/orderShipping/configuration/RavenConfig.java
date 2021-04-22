package com.raven.orderShipping.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import com.raven.orderShipping.publisher.ProcesserPublisher;

@Configuration
public class RavenConfig {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@Bean
	ProcesserPublisher processorPublisher() {
		return new ProcesserPublisher("processor", kafkaTemplate);
	}

}
