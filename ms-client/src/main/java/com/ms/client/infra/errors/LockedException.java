package com.ms.client.infra.errors;

public class LockedException extends RuntimeException {
	public LockedException(String message) {
		super(message);
	}
}
