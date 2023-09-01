package com.ms.client.domain.validation;

import com.ms.client.domain.errors.FormatException;
import com.ms.client.domain.errors.IncorrectValueException;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ManagerValidator {

	public static ManagerValidationBuilder validate(String value){
		return new ManagerValidationBuilder(value);
	}

	public static class ManagerValidationBuilder extends GenericValidator {

		private final String value;

		public ManagerValidationBuilder(String value) {
			super(value);
			this.value = value;
		}

		public ManagerValidationBuilder isEmpty(String message) {
			return (ManagerValidationBuilder) super.isEmpty(message);
		}

		public ManagerValidationBuilder isIdentifier() {
			return (ManagerValidationBuilder) super.isIdentifier();
		}

		public ManagerValidationBuilder isCpf() {
			Pattern pattern = Pattern.compile("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
			Matcher matcher = pattern.matcher(value);

			if (!matcher.matches()) {
				throw new FormatException("The CPF must be entered in the format: xxx.xxx.xxx-xx.");
			}
			return this;
		}

		public ManagerValidationBuilder isBornDate() {
			LocalDate date = LocalDate.parse(value);
			LocalDate now = LocalDate.now();
			int age = now.getYear() - date.getYear();

			if (date.isAfter(now)) {
				throw new IncorrectValueException("Date of birth cannot be in the future.");
			}
			return this;
		}

		public ManagerValidationBuilder isLegalAge() {
			LocalDate date = LocalDate.parse(value);
			LocalDate now = LocalDate.now();
			int age = now.getYear() - date.getYear();

			if (age < 18) {
				throw new IncorrectValueException("Only older than 18 years can register.");
			}
			return this;
		}


		public ManagerValidationBuilder isEmail() {
			return (ManagerValidationBuilder) super.isEmail();
		}

		public ManagerValidationBuilder isPhone() {
			return (ManagerValidationBuilder) super.isPhone();
		}

		public ManagerValidationBuilder isPassword() {
			Pattern pattern = Pattern.compile(
			  "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
			  Pattern.CASE_INSENSITIVE
			);
			Matcher matcher = pattern.matcher(value);

			if (!matcher.matches()) {
				throw new IncorrectValueException(
				  "Password need minimum eight characters, at least one letter and one number."
				);
			}
			return this;
		}
	}
}
