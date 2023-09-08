package com.ms.email.infra.errors;

public class NotFoundException extends RuntimeException {
	public static final String name = "NotFoundException";

	public NotFoundException(String message) {
		super(message);
	}
}
