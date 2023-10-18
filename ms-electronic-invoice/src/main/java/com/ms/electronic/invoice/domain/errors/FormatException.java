package com.ms.electronic.invoice.domain.errors;

public class FormatException extends RuntimeException {
	public FormatException(String message) {
		super(message);
	}
}
