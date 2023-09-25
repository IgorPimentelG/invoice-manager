package com.ms.email.infra.errors;

public class UnauthorizedException extends RuntimeException {

	public final static String name = "UnauthorizedException";

	public UnauthorizedException() {
		super("You don't have authorization to access this.");
	}
}
