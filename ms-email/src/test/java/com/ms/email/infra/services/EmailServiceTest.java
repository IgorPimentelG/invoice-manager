package com.ms.email.infra.services;

import com.ms.email.domain.entities.Email;
import com.ms.email.domain.types.Status;
import com.ms.email.infra.mocks.MockEmail;
import com.ms.email.infra.repositories.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmailServiceTest {

	MockEmail mockEmail;

	@InjectMocks
	EmailService service;

	@Mock
	EmailRepository repository;

	@Mock
	JavaMailSender emailSender;

	@BeforeEach
	void setup() {
		mockEmail = new MockEmail();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("should send an email")
	void sendEmailTest() throws Exception {
		Email email = mockEmail.createEntity();

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(email.getFrom());
		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getContent());

		service.send(email);

		assertEquals(email.getStatus(), Status.SENT);
		verify(emailSender, times(1)).send(message);
	}

	@Test
	@DisplayName("should update email status when email is not sent")
	void updateEmailStatusTest() throws Exception {
		Email email = mockEmail.createEntity();

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(email.getFrom());
		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getContent());

		doThrow(new MailException("Simulated Exception") {}).when(emailSender).send(message);

		service.send(email);

		assertEquals(email.getStatus(), Status.ERROR);
		verify(emailSender, times(1)).send(message);
	}
}
