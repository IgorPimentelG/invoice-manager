package com.ms.tax.calculator.infra.errors;

public class NoTaxesPayableException extends RuntimeException {

	public static final String name = "NoTaxesPayableException";

	public NoTaxesPayableException() {
		super("You don't have taxes to pay.");
	}
}
