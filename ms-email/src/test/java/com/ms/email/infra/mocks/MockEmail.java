package com.ms.email.infra.mocks;

import com.ms.email.domain.entities.Email;
import com.ms.email.domain.types.Status;

import java.util.ArrayList;
import java.util.List;

public class MockEmail {

	public Email createEntity() {
		return createEntity(0);
	}

	public List<Email> createListEntity() {
		List<Email> records = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			records.add(createEntity(i));
		}

		return records;
	}

	public Email createEntity(int index) {
		return new Email(
		  "dcdafd9b-369a-4c3f-9df6-770c8428ff35",
		  "72c02d05-feff-4a37-9b91-dbb01cedfa46",
		  "any_from" + index + "@mail.com",
		  "any_to" + index + "@mail.com",
		  "any_subject",
		  "any_content",
		  Status.SENT,
		  1693255833552L
		);
	}
}
