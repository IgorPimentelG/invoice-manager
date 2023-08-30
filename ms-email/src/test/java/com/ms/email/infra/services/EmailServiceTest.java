package com.ms.email.infra.services;

import com.ms.email.domain.entities.Email;
import com.ms.email.domain.errors.NotFoundException;
import com.ms.email.domain.types.Status;
import com.ms.email.infra.mocks.MockEmail;
import com.ms.email.infra.repositories.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;
import java.util.Optional;

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
		verify(repository, times(1)).save(any());
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
		verify(repository, times(1)).save(any());
		verify(emailSender, times(1)).send(message);
	}

	@Test
	@DisplayName("should find an email record")
	void findEmailRecordTest() throws Exception {
		Email email = mockEmail.createEntity();

		when(repository.findById(any())).thenReturn(Optional.of(email));

		var result = service.findById(any());

		assertNotNull(result);
		assertEquals(email.getId(), result.getId());
		assertEquals(email.getTo(), result.getTo());
		assertEquals(email.getFrom(), result.getFrom());
		assertEquals(email.getContent(), result.getContent());
		assertEquals(email.getSubject(), result.getSubject());
		assertEquals(email.getStatus(), result.getStatus());
		assertEquals(email.getOwnerRef(), result.getOwnerRef());
		assertEquals(email.getCreatedAt(), result.getCreatedAt());
	}

	@Test
	@DisplayName("should throws NotFoundException when find an email record was not found")
	void throwsNotFoundExceptionWhenFindEmailRecordWasNotFoundTest() throws Exception {

		when(repository.findById(any())).thenReturn(Optional.empty());

		Exception exception = assertThrows(NotFoundException.class, () -> {
			service.findById(any());
		});

		String expectedMessage = "The register of email was not found.";
		String resultMessage = exception.getMessage();

		verify(repository, times(1)).findById(any());
		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should find all email records")
	void findAllEmailRecords() throws Exception {
		Page<Email> records = new PageImpl<>(mockEmail.createListEntity());
		Pageable pageable = PageRequest.of(0, 1);

		when(repository.findAll(pageable)).thenReturn(records);

		var result = service.findAll(pageable);

		assertNotNull(result);
		assertEquals(5, result.getTotalElements());
		verify(repository, times(1)).findAll(pageable);
	}
}
