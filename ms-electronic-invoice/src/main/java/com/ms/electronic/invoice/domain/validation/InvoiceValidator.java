package com.ms.electronic.invoice.domain.validation;

import com.ms.electronic.invoice.domain.errors.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class InvoiceValidator {

	public static InvoiceValidationBuilder validate(String value) {
		return new InvoiceValidationBuilder(value);
	}
	public static class InvoiceValidationBuilder extends GenericValidator {

		private final String value;

		public InvoiceValidationBuilder(String value) {
			super(value);
			this.value = value;
		}

		public InvoiceValidationBuilder isEmpty(String message) {
			return (InvoiceValidationBuilder) super.isEmpty(message);
		}

		public InvoiceValidationBuilder isFuture() {
			var date = LocalDateTime.parse(value);

			if (date.isAfter(LocalDateTime.now())) {
				throw new IncorrectValueException("Date cannot be in the future.");
			}
			return this;
		}

		public InvoiceValidationBuilder isPositive() {
			var valueConverter = BigDecimal.valueOf(Long.parseLong(value));

			if (valueConverter.compareTo(BigDecimal.ZERO) < 0) {
				throw new IncorrectValueException("Value cannot be negative.");
			}
			return this;
		}

		public InvoiceValidationBuilder isOnlyNumber() {
			Pattern pattern = Pattern.compile("^\\d+$");
			Matcher matcher = pattern.matcher(value);

			if (!matcher.matches()) {
				throw new FormatException("Invalid invoice number.");
			}
			return this;
		}

		public InvoiceValidationBuilder isReference() {
			Pattern pattern = Pattern.compile("^\\d{2}/\\d{4}$");
			Matcher matcher = pattern.matcher(value);

			if (!matcher.matches()) {
				throw new FormatException("Reference must be in the format: xx/xxxx.");
			}

			var values = value.split("/");
			var month = Integer.parseInt(values[0]);
			var year = Integer.parseInt(values[1]);

			if (month <= 0 || month > 12 ) {
				throw new IncorrectValueException("Invalid month.");
			} else if (year < LocalDate.EPOCH.getYear()) {
				throw new IncorrectValueException("Can't release notes from the past.");
			}
			return this;
		}
	}
}
