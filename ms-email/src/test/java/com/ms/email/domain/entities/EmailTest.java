package com.ms.email.domain.entities;

import com.ms.email.domain.errors.InvalidValueException;
import com.ms.email.domain.types.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

	@Test
	@DisplayName("should create a new email")
	void testCreateEmail() throws InvalidValueException {
		var result = new Email(
		  "dcdafd9b-369a-4c3f-9df6-770c8428ff35",
		  "72c02d05-feff-4a37-9b91-dbb01cedfa46",
		  "any_from@mail.com",
		  "any_to@mail.com",
		  "any_subject",
		  "any_content",
		  Status.SENT,
		  1693255833552L
		);

		assertNotNull(result);
	}
}
