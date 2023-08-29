package com.ms.email.domain.helpers;

import com.ms.email.domain.errors.InvalidValueException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Validation {

	public static void ref(String ref, String name) throws InvalidValueException {
		System.out.println();
		if (ref.length() != 36) {
			throw new InvalidValueException(name, "the value is not UUID");
		}
	}

	public static void emailAddress(String email) throws InvalidValueException {
		Pattern rgx = Pattern.compile(
		  "^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$",
		  Pattern.CASE_INSENSITIVE
		);
		Matcher matcher = rgx.matcher(email);
		if (!matcher.matches()) {
			throw new InvalidValueException("email", "it's not an email address");
		}
	}

	public static void emailToYourself(String from, String to) throws InvalidValueException {
		if (from.equals(to)) {
			throw new InvalidValueException("email", "is not possible send email to yourself");
		}
	}

	public static void isEmpty(String value, String name) throws InvalidValueException  {
		if (value == null || value.isEmpty() || value.isBlank()) {
			throw new InvalidValueException(name, "the value is empty");
		}
	}

	public static void isFuture(Long value, String name) throws InvalidValueException {
		LocalDateTime date = LocalDateTime.ofInstant(
		  Instant.ofEpochMilli(value),
		  TimeZone.getDefault().toZoneId()
		);

		if (date.isAfter(LocalDateTime.now())) {
			throw new InvalidValueException(name, "the date is before the current date");
		}
	}
}
