package com.ms.electronic.invoice.domain.validation;

import com.ms.electronic.invoice.domain.errors.FormatException;
import com.ms.electronic.invoice.domain.errors.IncorrectValueException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class GenericValidator {

	private final String value;

	public GenericValidator(String value) {
		this.value = value;
	}

	public GenericValidator isEmpty(String message) {
		if (value == null || value.isBlank()) {
			throw new IncorrectValueException(message);
		}
		return this;
	}

	public GenericValidator isPhone() {
		Pattern pattern = Pattern.compile("^\\(\\d\\d\\)\\s(\\d{5})-(\\d{4})$");
		Matcher matcher = pattern.matcher(value);

		if(!matcher.matches()) {
			throw new FormatException("The phone must be entered in the format: (xx) xxxxx-xxxx.");
		}
		return this;
	}
}
