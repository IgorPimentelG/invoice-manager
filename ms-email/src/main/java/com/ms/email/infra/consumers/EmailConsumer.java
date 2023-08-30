package com.ms.email.infra.consumers;

import com.ms.email.domain.entities.Email;
import com.ms.email.domain.errors.InvalidValueException;
import com.ms.email.domain.factories.EmailFactory;
import com.ms.email.infra.consumers.dtos.EmailDto;
import com.ms.email.infra.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class EmailConsumer {

	@Autowired
	private EmailService service;

	@RabbitListener(queues = "${spring.rabbitmq.queue}")
	public void listen(@Payload EmailDto emailDto) throws InvalidValueException {
		Email email = EmailFactory.create(
		  emailDto.to(),
		  emailDto.subject(),
		  emailDto.content(),
		  emailDto.ownerRef()
		);

		service.send(email);
	}
}
