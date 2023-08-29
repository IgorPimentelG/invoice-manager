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

	@Test
	@DisplayName("should throws an InvalidValueException when create a new email with invalid id")
	void testCreateEmailWithInvalidId() {

		Exception exception = assertThrows(InvalidValueException.class, () -> {
			new Email(
			  "invalid-id",
			  "72c02d05-feff-4a37-9b91-dbb01cedfa46",
			  "any_from@mail.com",
			  "any_to@mail.com",
			  "any_subject",
			  "any_content",
			  Status.SENT,
			  1693255833552L
			);
		});

		String expectedMessage = "The field id is not a valid value. Because the value is not UUID.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an InvalidValueException when create a new email with invalid owner ref")
	void testCreateEmailWithInvalidOwnerRef() {

		Exception exception = assertThrows(InvalidValueException.class, () -> {
			new Email(
			  "dcdafd9b-369a-4c3f-9df6-770c8428ff35",
			  "invalid-owner-ref",
			  "any_from@mail.com",
			  "any_to@mail.com",
			  "any_subject",
			  "any_content",
			  Status.SENT,
			  1693255833552L
			);
		});

		String expectedMessage = "The field ownerRef is not a valid value. Because the value is not UUID.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an InvalidValueException when create a new email with invalid sender")
	void testCreateEmailWithInvalidSender() {

		Exception exception = assertThrows(InvalidValueException.class, () -> {
			new Email(
			  "dcdafd9b-369a-4c3f-9df6-770c8428ff35",
			  "72c02d05-feff-4a37-9b91-dbb01cedfa46",
			  "invalid-email",
			  "any_to@mail.com",
			  "any_subject",
			  "any_content",
			  Status.SENT,
			  1693255833552L
			);
		});

		String expectedMessage = "The field email is not a valid value. Because it's not an email address.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an InvalidValueException when create a new email with invalid recipient")
	void testCreateEmailWithInvalidRecipient() {

		Exception exception = assertThrows(InvalidValueException.class, () -> {
			new Email(
			  "dcdafd9b-369a-4c3f-9df6-770c8428ff35",
			  "72c02d05-feff-4a37-9b91-dbb01cedfa46",
			  "any_from@mail.coml",
			  "invalid-email",
			  "any_subject",
			  "any_content",
			  Status.SENT,
			  1693255833552L
			);
		});

		String expectedMessage = "The field email is not a valid value. Because it's not an email address.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}
}
