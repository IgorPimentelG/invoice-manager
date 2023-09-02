package com.ms.client.domain.entities;

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
}