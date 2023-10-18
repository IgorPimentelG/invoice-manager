package com.ms.tax.calculator.infra.errors;

public class NoTaxesPayableException extends RuntimeException {
	public NoTaxesPayableException() {
		super("You don't have taxes to pay.");
	}
}
