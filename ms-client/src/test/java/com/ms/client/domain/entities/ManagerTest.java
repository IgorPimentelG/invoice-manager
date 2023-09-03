package com.ms.client.domain.entities;

import com.ms.client.domain.errors.FormatException;
import com.ms.client.domain.errors.IncorrectValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.Format;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {

	@Test
	@DisplayName("should create a user manager")
	void testCreateUserManager() {
		var result = new Manager(
		  "a7df6b1c-5c98-4317-b112-3407cae1406a",
		  "000.000.000-00",
		  "any full name",
		  LocalDate.parse("1990-01-01"),
		  "(00) 00000-0000",
		  "any@mail.com",
		  "anyPassword0"
		);

		assertNotNull(result);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with empty id")
	void testCreateUserManagerWithEmptyId() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Manager(
			  "",
			  "000.000.000-00",
			  "any full name",
			  LocalDate.parse("1990-01-01"),
			  "(00) 00000-0000",
			  "any@mail.com",
			  "anyPassword0"
			);
		});

		String expectedMessage = "ID must not be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with incorrect id format")
	void testCreateUserManagerWithIncorrectIdFormat() {
		Exception exception = assertThrows(FormatException.class, () -> {
			new Manager(
			  "1L",
			  "000.000.000-00",
			  "any full name",
			  LocalDate.parse("1990-01-01"),
			  "(00) 00000-0000",
			  "any@mail.com",
			  "anyPassword0"
			);
		});

		String expectedMessage = "ID must be a UUID.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with empty CPF")
	void testCreateUserManagerWithEmptyCPF() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Manager(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "",
			  "any full name",
			  LocalDate.parse("1990-01-01"),
			  "(00) 00000-0000",
			  "any@mail.com",
			  "anyPassword0"
			);
		});

		String expectedMessage = "CPF must not be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with incorrect CPF format")
	void testCreateUserManagerWithIncorrectCPFFormat() {
		Exception exception = assertThrows(FormatException.class, () -> {
			new Manager(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "00000000000",
			  "any full name",
			  LocalDate.parse("1990-01-01"),
			  "(00) 00000-0000",
			  "any@mail.com",
			  "anyPassword0"
			);
		});

		String expectedMessage = "The CPF must be entered in the format: xxx.xxx.xxx-xx.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with empty Full Name")
	void testCreateUserManagerWithEmptyFullName() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Manager(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "000.000.000-00",
			  "",
			  LocalDate.parse("1990-01-01"),
			  "(00) 00000-0000",
			  "any@mail.com",
			  "anyPassword0"
			);
		});

		String expectedMessage = "Full name must not be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with born date in the future")
	void testCreateUserManagerWithBornDateInTheFuture() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Manager(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "000.000.000-00",
			  "any full name",
			  LocalDate.parse("2030-01-01"),
			  "(00) 00000-0000",
			  "any@mail.com",
			  "anyPassword0"
			);
		});

		String expectedMessage = "Date of birth cannot be in the future.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with born date illegal")
	void testCreateUserManagerWithBornDateIllegal() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Manager(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "000.000.000-00",
			  "any full name",
			  LocalDate.now(),
			  "(00) 00000-0000",
			  "any@mail.com",
			  "anyPassword0"
			);
		});

		String expectedMessage = "Only older than 18 years can register.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with empty phone")
	void testCreateUserManagerWithEmptyPhone() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Manager(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "000.000.000-00",
			  "any full name",
			  LocalDate.parse("1990-01-01"),
			  "",
			  "any@mail.com",
			  "anyPassword0"
			);
		});

		String expectedMessage = "Phone must not be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with incorrect phone format")
	void testCreateUserManagerWithIncorrectPhoneFormat() {
		Exception exception = assertThrows(FormatException.class, () -> {
			new Manager(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "000.000.000-00",
			  "any full name",
			  LocalDate.parse("1990-01-01"),
			  "00000000000",
			  "any@mail.com",
			  "anyPassword0"
			);
		});

		String expectedMessage = "The phone must be entered in the format: (xx) xxxxx-xxxx.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with empty email")
	void testCreateUserManagerWithEmptyEmail() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Manager(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "000.000.000-00",
			  "any full name",
			  LocalDate.parse("1990-01-01"),
			  "(00) 00000-0000",
			  "",
			  "anyPassword0"
			);
		});

		String expectedMessage = "Email must not be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with incorrect email format")
	void testCreateUserManagerWithIncorrectEmailFormat() {
		Exception exception = assertThrows(FormatException.class, () -> {
			new Manager(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "000.000.000-00",
			  "any full name",
			  LocalDate.parse("1990-01-01"),
			  "(00) 00000-0000",
			  "any@",
			  "anyPassword0"
			);
		});

		String expectedMessage = "The email must be entered in the format: exemple@example.com";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with empty password")
	void testCreateUserManagerWithEmptyPassword() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Manager(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "000.000.000-00",
			  "any full name",
			  LocalDate.parse("1990-01-01"),
			  "(00) 00000-0000",
			  "any@mail.com",
			  ""
			);
		});

		String expectedMessage = "Password must not be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a user manager with incorrect password format")
	void testCreateUserManagerWithIncorrectPasswordFormat() {
		Exception exception = assertThrows(FormatException.class, () -> {
			new Manager(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "000.000.000-00",
			  "any full name",
			  LocalDate.parse("1990-01-01"),
			  "(00) 00000-0000",
			  "any@mail.com",
			  "123"
			);
		});

		String expectedMessage = "Password need minimum eight characters, at least one letter and one number.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}
}