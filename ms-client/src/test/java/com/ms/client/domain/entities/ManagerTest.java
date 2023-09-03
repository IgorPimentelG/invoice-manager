package com.ms.client.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}