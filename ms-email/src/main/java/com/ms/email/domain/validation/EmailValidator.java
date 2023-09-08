package com.ms.email.domain.validation;

import com.ms.email.domain.errors.IncorrectValueException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class EmailValidator {

	public static EmailValidationBuilder validate(String value) {
		return new EmailValidationBuilder(value);
	}

	public static class EmailValidationBuilder {

		private final String value;

		public EmailValidationBuilder(String value) {
			this.value = value;
		}

		public EmailValidationBuilder isRef(String name) {
			if (value.length() != 36) {
				throw new IncorrectValueException(name, "the value is not UUID");
			}
			return this;
		}

		public EmailValidationBuilder isEmail() {
			Pattern rgx = Pattern.compile(
			  "^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$",
			  Pattern.CASE_INSENSITIVE
			);
			Matcher matcher = rgx.matcher(value);

			if (!matcher.matches()) {
				throw new IncorrectValueException("email", "it's not an email address");
			}
			return this;
		}

		public EmailValidationBuilder isEmailToYourself(String from) {
			if (from.equals(value)) {
				throw new IncorrectValueException("email", "is not possible send email to yourself");
			}
			return this;
		}

		public EmailValidationBuilder isEmpty(String name) {
			if (value == null || value.isEmpty() || value.isBlank()) {
				throw new IncorrectValueException(name, "the value is empty");
			}
			return this;
		}

		public EmailValidationBuilder isFuture(String name) {
			LocalDateTime date = LocalDateTime.ofInstant(
			  Instant.ofEpochMilli(Long.parseLong(value)),
			  TimeZone.getDefault().toZoneId()
			);

			if (date.isAfter(LocalDateTime.now())) {
				throw new IncorrectValueException(name, "the date is before the current date");
			}
			return this;
		}
	}
}
