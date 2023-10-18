package com.ms.electronic.invoice.domain.errors;

public class IncorrectValueException extends RuntimeException {
	public IncorrectValueException(String message) {
		super(message);
	}
}
