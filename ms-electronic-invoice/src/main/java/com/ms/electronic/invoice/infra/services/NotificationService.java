package com.ms.electronic.invoice.infra.services;

import com.ms.electronic.invoice.infra.dtos.EmailRequestDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${queues.notification}")
	private String routingKey;

	public void sendNotification(String to, String content, String ownerRef) {
		rabbitTemplate.convertAndSend(
		  routingKey,
		  new EmailRequestDto(to, "Invoice Manager - Support", content, ownerRef)
		);
	}
}
