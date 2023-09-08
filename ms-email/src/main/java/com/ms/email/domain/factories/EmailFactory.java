package com.ms.email.domain.factories;

import com.ms.email.domain.entities.Email;
import com.ms.email.domain.errors.IncorrectValueException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public abstract class EmailFactory {

	private static final String SENDER = "no-reply@invoice-manager.com";

	public static Email create(String to, String subject, String content, String owner) {
		Email email = new Email();
		email.setOwnerRef(owner);
		email.setFrom(SENDER);
		email.setTo(to);
		email.setSubject(subject);
		email.setContent(content);
		email.setCreatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

		return email;
	}
}
