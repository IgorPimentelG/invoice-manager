package com.ms.client.domain.validation;

import com.ms.client.domain.errors.FormatException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CompanyValidator {

	public static CompanyValidationBuilder validate(String value) {
		return new CompanyValidationBuilder(value);
	}

	public static class CompanyValidationBuilder extends GenericValidator {

		private final String value;

		public CompanyValidationBuilder(String value) {
			super(value);
			this.value = value;
		}

		public CompanyValidationBuilder isEmpty(String message) {
			return (CompanyValidationBuilder) super.isEmpty(message);
		}

		public CompanyValidationBuilder isIdentifier() {
			return (CompanyValidationBuilder) super.isIdentifier();
		}

		public CompanyValidationBuilder isCnpj() {
			Pattern pattern = Pattern.compile("^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}");
			Matcher matcher = pattern.matcher(value);

			if (!matcher.matches()) {
				throw new FormatException("The CNPJ must be entered in the format: xx.xxx.xxx/xxxx-xx.");
			}
			return this;
		}

		public CompanyValidationBuilder isEmail() {
			return (CompanyValidationBuilder) super.isEmail();
		}

		public CompanyValidationBuilder isPhone() {
			return (CompanyValidationBuilder) super.isPhone();
		}
	}
}
