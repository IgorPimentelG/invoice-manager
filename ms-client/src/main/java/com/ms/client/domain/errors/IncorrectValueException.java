package com.ms.client.domain.errors;

public class IncorrectValueException extends RuntimeException {
	public IncorrectValueException(String message) {
		super(message);
	}
}
