package com.ms.email.infra.errors;

public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super(message);
	}
}
