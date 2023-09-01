package com.ms.client.domain.errors;

public class FormatException extends RuntimeException {

	public static final String name = "FormatException";

	public FormatException(String message) {
		super(message);
	}
}
