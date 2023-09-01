package com.ms.client.domain.validation;

import com.ms.client.domain.errors.FormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AddressValidator {

	public static AddressValidationBuilder validate(String value) {
		return new AddressValidationBuilder(value);
	}

	public static class AddressValidationBuilder extends GenericValidator {
		private final String value;

		public AddressValidationBuilder(String value) {
			super(value);
			this.value = value;
		}

		public AddressValidationBuilder isEmpty(String message) {
			return (AddressValidationBuilder) super.isEmpty(message);
		}

		public AddressValidationBuilder isState() {
			Pattern pattern = Pattern.compile("^[A-Z][A-Z]$", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(value);

			if (!matcher.matches()) {
				throw new FormatException("The state must be only the abbreviation in capital letters.");
			}
			return this;
		}

		public AddressValidationBuilder isAddressNumber() {
			Pattern pattern = Pattern.compile("^\\d+(-[A-Z]+)?$", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(value);

			if (!matcher.matches()) {
				throw new FormatException("Number is not valid. Insert in the following format: 00 or 000-A.");
			}
			return this;
		}

		public AddressValidationBuilder isZipCode() {
			Pattern pattern = Pattern.compile("^\\d{5}-\\d{3}$");
			Matcher matcher = pattern.matcher(value);

			if (!matcher.matches()) {
				throw new FormatException("Zip code is not valid. Insert in the following format: xxxxx-xx.");
			}
			return this;
		}
	}
}
