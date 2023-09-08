package com.ms.email.infra.services;

import com.ms.email.domain.entities.Email;
import com.ms.email.infra.errors.NotFoundException;
import com.ms.email.domain.types.Status;
import com.ms.email.infra.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailService {

	@Autowired
	private EmailRepository repository;

	@Autowired
	private JavaMailSender emailSender;

	private final Logger logger = Logger.getLogger(EmailService.class.getName());

	public Email send(Email email) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(email.getFrom());
			message.setTo(email.getTo());
			message.setSubject(email.getSubject());
			message.setText(email.getContent());

			emailSender.send(message);

			email.setStatus(Status.SENT);

			logger.log(Level.INFO, "The email sent successfully");
		} catch (Exception e) {
			email.setStatus(Status.ERROR);

			logger.log(Level.WARNING, "The email failed to be sent");
		}

		return repository.save(email);
	}

	public Email findById(String id) {
		var entity =  repository.findById(id).orElseThrow(
		  () -> {
			  logger.log(Level.WARNING, "Email record not found.");
			  return new NotFoundException("The register of email was not found.");
		  }
		);

		logger.log(Level.INFO, "The email record was found.");

		return entity;
	}

	public Page<Email> findAll(Pageable pageable) {
		logger.log(Level.INFO, "List all email records.");
		return repository.findAll(pageable);
	}
}
