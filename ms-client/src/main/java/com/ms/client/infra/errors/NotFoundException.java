package com.ms.client.infra.errors;

public class NotFoundException extends RuntimeException {

	public static String name = "NotFoundException";

	public NotFoundException(String entity) {
		super(entity + " not found.");
	}
}
