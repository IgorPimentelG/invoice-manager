package com.ms.tax.calculator.infra.errors;

public class NotFoundException extends RuntimeException {

	public static final String name = "NotFoundException";

	public NotFoundException(String message) {
		super(message);
	}
}
