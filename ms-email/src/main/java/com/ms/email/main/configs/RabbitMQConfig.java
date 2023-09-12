package com.ms.email.main.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${queues.notification}")
	private String queueName;

	@Bean
	public Queue queue() {
		return new Queue(queueName);
	}

	@Bean
	public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public ApplicationListener<ApplicationReadyEvent> applicationReadyEventApplicationListener(
	  RabbitAdmin rabbitAdmin
	) {
		return event -> rabbitAdmin.initialize();
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(
	  ConnectionFactory connectionFactory,
	  Jackson2JsonMessageConverter messageConverter
	) {
		var rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}
}
