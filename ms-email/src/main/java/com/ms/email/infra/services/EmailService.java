package com.ms.email.infra.services;

import com.ms.email.domain.entities.Email;
import com.ms.email.domain.errors.NotFoundException;
import com.ms.email.domain.types.Status;
import com.ms.email.infra.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

	@Autowired
	private EmailRepository repository;

	@Autowired
	private JavaMailSender emailSender;

	public void send(Email email) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(email.getFrom());
			message.setTo(email.getTo());
			message.setSubject(email.getSubject());
			message.setText(email.getContent());

			emailSender.send(message);

			email.setStatus(Status.SENT);
		} catch (Exception e) {
			email.setStatus(Status.ERROR);
		}

		repository.save(email);
	}

	public Email findById(String id) throws NotFoundException {
		return repository.findById(id).orElseThrow(
		  () -> new NotFoundException("The register of email was not found.")
		);
	}

	public Page<Email> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
}
