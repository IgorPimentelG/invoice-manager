package com.ms.email.domain.errors;

public class IncorrectValueException extends RuntimeException {
	public IncorrectValueException(String field, String cause) {
		super("The field " + field + " is not a valid value. Because " + cause + ".");
	}

	public IncorrectValueException(String message) {
		super(message);
	}
}
