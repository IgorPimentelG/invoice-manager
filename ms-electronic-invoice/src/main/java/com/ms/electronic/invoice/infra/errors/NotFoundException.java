package com.ms.electronic.invoice.infra.errors;

public class NotFoundException extends RuntimeException{

	public static final String name = "BadRequestException";

	public NotFoundException(String message) {
		super(message);
	}
}
