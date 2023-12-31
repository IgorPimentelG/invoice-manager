package com.ms.email.domain.entities;

import com.ms.email.domain.errors.IncorrectValueException;
import com.ms.email.domain.factories.EmailFactory;
import com.ms.email.domain.types.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

	@Test
	@DisplayName("should create a new email")
	void testCreateEmail() throws IncorrectValueException {
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
	@DisplayName("should create a new email with factory")
	void testCreateEmailWithFactory() {
		var result = EmailFactory.create(
		  "any_to@mail.com",
		  "any_subject",
		  "any_content",
		  "72c02d05-feff-4a37-9b91-dbb01cedfa46"
		);

		assertNotNull(result);
	}

	@Test
	@DisplayName("should throws an InvalidValueException when create a new email with invalid id")
	void testCreateEmailWithInvalidId() {

		Exception exception = assertThrows(IncorrectValueException.class, () -> {
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

		Exception exception = assertThrows(IncorrectValueException.class, () -> {
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

		Exception exception = assertThrows(IncorrectValueException.class, () -> {
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

		Exception exception = assertThrows(IncorrectValueException.class, () -> {
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

	@Test
	@DisplayName("should throws an InvalidValueException when create a new email with empty subject")
	void testCreateEmailWithEmptySubject() {

		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Email(
			  "dcdafd9b-369a-4c3f-9df6-770c8428ff35",
			  "72c02d05-feff-4a37-9b91-dbb01cedfa46",
			  "any_from@mail.coml",
			  "any_to@mail.coml",
			  " ",
			  "any_content",
			  Status.SENT,
			  1693255833552L
			);
		});

		String expectedMessage = "The field subject is not a valid value. Because the value is empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an InvalidValueException when create a new email with empty content")
	void testCreateEmailWithEmptyContent() {

		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Email(
			  "dcdafd9b-369a-4c3f-9df6-770c8428ff35",
			  "72c02d05-feff-4a37-9b91-dbb01cedfa46",
			  "any_from@mail.coml",
			  "any_to@mail.coml",
			  "any_subject",
			  " ",
			  Status.SENT,
			  1693255833552L
			);
		});

		String expectedMessage = "The field content is not a valid value. Because the value is empty.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}

	@Test
	@DisplayName("should throws an InvalidValueException when create a new email with date in the future")
	void testCreateEmailWithDateInTheFuture() {

		Exception exception = assertThrows(IncorrectValueException.class, () -> {
			new Email(
			  "dcdafd9b-369a-4c3f-9df6-770c8428ff35",
			  "72c02d05-feff-4a37-9b91-dbb01cedfa46",
			  "any_from@mail.coml",
			  "any_to@mail.coml",
			  "any_subject",
			  "any_content",
			  Status.SENT,
			  576229950000000L
			);
		});

		String expectedMessage = "The field createdAt is not a valid value. Because the date is before the current date.";
		String resultMessage = exception.getMessage();

		assertEquals(expectedMessage, resultMessage);
	}
}
