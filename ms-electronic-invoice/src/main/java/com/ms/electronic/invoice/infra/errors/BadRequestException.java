package com.ms.electronic.invoice.infra.errors;

public class BadRequestException extends RuntimeException{

	public static final String name = "BadRequestException";

	public BadRequestException(String message) {
		super(message);
	}
}
