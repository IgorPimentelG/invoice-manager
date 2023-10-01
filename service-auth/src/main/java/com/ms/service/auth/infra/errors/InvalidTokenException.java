package com.ms.service.auth.infra.errors;

public class InvalidTokenException extends RuntimeException {

	public static final String name = "InvalidTokenException";

	public InvalidTokenException(String message) {
		super(message);
	}

	public InvalidTokenException() {
		super("Credentials are invalid.");
	}
}
