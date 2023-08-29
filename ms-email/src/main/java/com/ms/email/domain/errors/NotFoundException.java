package com.ms.email.domain.errors;

public class NotFoundException extends Exception {
	public static final String name = "NotFoundException";

	public NotFoundException(String message) {
		super(message);
	}
}
