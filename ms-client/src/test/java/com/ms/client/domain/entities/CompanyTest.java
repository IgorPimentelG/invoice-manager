package com.ms.client.domain.entities;

import com.ms.client.domain.errors.FormatException;
import com.ms.client.domain.errors.IncorrectValueException;
import com.ms.client.domain.types.TaxRegime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyTest {

	Manager manager;

	@BeforeEach
	void setup() {
		manager = new Manager(
		  "a7df6b1c-5c98-4317-b112-3407cae1406a",
		  "000.000.000-00",
		  "any full name",
		  LocalDate.parse("1990-01-01"),
		  "(00) 00000-0000",
		  "any@mail.com",
		  "anyPassword0"
		);
	}

	@Test
	@DisplayName("should create a company")
	void testCreateCompany() {
		var result = new Company(
		  "4ff6d786-b7a1-4efc-be85-a297e04c2f48",
		  "any corporate name",
		  TaxRegime.SIMPLE_NATIONAL,
		  "00.000.000/0000-00",
		  "any@mail.com",
		  "(00) 00000-0000",
		  manager
		);

		assertNotNull(result);
	}

	@Test
	@DisplayName("should throw an exception when create a company with incorrect id format")
	void testCreateCompanyWithIncorrectIdFormat() {
		Exception exception = assertThrows(FormatException.class, () -> {
			new Company(
			  "1L",
			  "any corporate name",
			  TaxRegime.SIMPLE_NATIONAL,
			  "00.000.000/0000-00",
			  "any@mail.com",
			  "(00) 00000-0000",
			  manager
			);
		});

		String expectedMessage = "ID must be a UUID.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a company with empty id")
	void testCreateUserManagerWithEmptyId() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Company(
			  "",
			  "any corporate name",
			  TaxRegime.SIMPLE_NATIONAL,
			  "00.000.000/0000-00",
			  "any@mail.com",
			  "(00) 00000-0000",
			  manager
			);
		});

		String expectedMessage = "ID must not be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a company with empty corporate name")
	void testCreateUserManagerWithEmptyCorporateName() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Company(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "",
			  TaxRegime.SIMPLE_NATIONAL,
			  "00.000.000/0000-00",
			  "any@mail.com",
			  "(00) 00000-0000",
			  manager
			);
		});

		String expectedMessage = "Corporate name must not be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a company with empty CNPJ")
	void testCreateUserManagerWithEmptyCNPJ() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Company(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "any corporate name",
			  TaxRegime.SIMPLE_NATIONAL,
			  "",
			  "any@mail.com",
			  "(00) 00000-0000",
			  manager
			);
		});

		String expectedMessage = "CNPJ must not be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throw an exception when create a company with incorrect CNPJ format")
	void testCreateUserManagerWithIncorrectCNPJFormat() {
		Exception exception = assertThrows(FormatException.class, () -> {
			new Company(
			  "a7df6b1c-5c98-4317-b112-3407cae1406a",
			  "any corporate name",
			  TaxRegime.SIMPLE_NATIONAL,
			  "00000000000000",
			  "any@mail.com",
			  "(00) 00000-0000",
			  manager
			);
		});

		String expectedMessage = "The CNPJ must be entered in the format: xx.xxx.xxx/xxxx-xx.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}
}