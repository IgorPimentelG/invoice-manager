package com.ms.email.domain.errors;

public class InvalidValueException extends Exception {

	public static final String name = "InvalidValueException";

	public InvalidValueException(String field, String cause) {
		super("The field " + field + " is not a valid value. Because " + cause + ".");
	}

	public InvalidValueException(String message) {
		super(message);
	}
}
