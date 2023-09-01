package com.ms.client.domain.errors;

public class IncorrectValueException extends RuntimeException {

	public static final String name = "IncorrectValueException";

	public IncorrectValueException(String message) {
		super(message);
	}
}
