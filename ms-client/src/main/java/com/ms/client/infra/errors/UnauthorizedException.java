package com.ms.client.infra.errors;

public class UnauthorizedException extends  RuntimeException {

	public static final String name = "UnauthorizedException";

	public UnauthorizedException() {
		super("You don't have authorization to access this.");
	}
}
