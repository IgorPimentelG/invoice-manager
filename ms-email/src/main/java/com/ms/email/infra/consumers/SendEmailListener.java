package com.ms.email.infra.consumers;

import com.ms.email.domain.entities.Email;
import com.ms.email.domain.factories.EmailFactory;
import com.ms.email.infra.dtos.EmailDto;
import com.ms.email.infra.services.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendEmailListener {

	@Autowired
	private EmailService service;

	@RabbitListener(queues = "ms-email.v1.send-email")
	public void onSendEmail(EmailDto data) {
		Email email = EmailFactory.create(
		  data.to(),
		  data.subject(),
		  data.content(),
		  data.ownerRef()
		);
		service.send(email);
	}
}
