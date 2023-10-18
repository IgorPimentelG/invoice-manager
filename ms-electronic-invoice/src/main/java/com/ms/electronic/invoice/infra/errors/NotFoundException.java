package com.ms.electronic.invoice.infra.errors;

public class NotFoundException extends RuntimeException{
	public NotFoundException(String message) {
		super(message);
	}
}
