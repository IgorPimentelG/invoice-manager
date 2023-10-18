package com.ms.electronic.invoice.infra.errors;

public class BadRequestException extends RuntimeException{
	public BadRequestException(String message) {
		super(message);
	}
}
