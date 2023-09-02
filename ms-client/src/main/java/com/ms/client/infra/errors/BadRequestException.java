package com.ms.client.infra.errors;

public class BadRequestException extends RuntimeException {

	public static String name = "BadRequestException";

	public BadRequestException(String message) {
		super(message);
	}
}
