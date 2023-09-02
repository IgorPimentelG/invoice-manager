package com.ms.client.domain.entities;

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
}