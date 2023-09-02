package com.ms.client.domain.entities;

import com.ms.client.domain.errors.FormatException;
import com.ms.client.domain.errors.IncorrectValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {

	@Test
	@DisplayName("should create a new address")
	void testCreateAddress() {
		var result = new Address(
		  1L,
		  "XX",
		  "any city",
		  "any street",
		  "any neighborhood",
		  "00-X",
		  "00000-000"
		);

		assertNotNull(result);
	}

	@Test
	@DisplayName("should throws an exception when create a address with empty state")
	void testCreateAddressWithEmptyState() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			var result = new Address(
			  1L,
			  "",
			  "any city",
			  "any street",
			  "any neighborhood",
			  "00-X",
			  "00000-000"
			);
		});

		String expectedMessage = "State cannot be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an exception when create a address with incorrect state format")
	void testCreateAddressWithIncorrectStateFormat() {
		Exception exception = assertThrows(FormatException.class, () -> {
			var result = new Address(
			  1L,
			  "any state",
			  "any city",
			  "any street",
			  "any neighborhood",
			  "00-X",
			  "00000-000"
			);
		});

		String expectedMessage = "The state must be only the abbreviation in capital letters.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an exception when create a address with empty city")
	void testCreateAddressWithEmptyCity() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			var result = new Address(
			  1L,
			  "XX",
			  "",
			  "any street",
			  "any neighborhood",
			  "00-X",
			  "00000-000"
			);
		});

		String expectedMessage = "City cannot be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an exception when create a address with empty street")
	void testCreateAddressWithEmptyStreet() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			var result = new Address(
			  1L,
			  "XX",
			  "any city",
			  "",
			  "any neighborhood",
			  "00-X",
			  "00000-000"
			);
		});

		String expectedMessage = "Street cannot be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an exception when create a address with empty neighborhood")
	void testCreateAddressWithEmptyNeighborhood() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			var result = new Address(
			  1L,
			  "XX",
			  "any city",
			  "any street",
			  "",
			  "00-X",
			  "00000-000"
			);
		});

		String expectedMessage = "Neighborhood cannot be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an exception when create a address with empty number")
	void testCreateAddressWithEmptyNumber() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			var result = new Address(
			  1L,
			  "XX",
			  "any city",
			  "any street",
			  "any neighborhood",
			  "",
			  "00000-000"
			);
		});

		String expectedMessage = "Number cannot be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an exception when create a address with incorrect number format")
	void testCreateAddressWithIncorrectNumberFormat() {
		Exception exception = assertThrows(FormatException.class, () -> {
			var result = new Address(
			  1L,
			  "XX",
			  "any city",
			  "any street",
			  "any neighborhood",
			  "A00",
			  "00000-000"
			);
		});

		String expectedMessage = "Number is not valid. Insert in the following format: 00 or 000-A.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an exception when create a address with empty zip code")
	void testCreateAddressWithEmptyZipCode() {
		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			var result = new Address(
			  1L,
			  "XX",
			  "any city",
			  "any street",
			  "any neighborhood",
			  "00",
			  ""
			);
		});

		String expectedMessage = "Zip code cannot be empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}
}