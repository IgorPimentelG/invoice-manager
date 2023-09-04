package com.ms.client.infra.mocks;

import com.ms.client.domain.entities.Address;

import java.util.ArrayList;
import java.util.List;

public class MockAddress {

	public Address createEntity() {
		return createEntity(0);
	}

	public Address createEntity(int number) {
		return new Address(
		  Long.parseLong(String.valueOf(number)),
		  "XX",
		  "any city",
		  "any street",
		  "any neighborhood",
		  "00-X",
		  "00000-000"
		);
	}

	public List<Address> createListEntity() {
		List<Address> addresses = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			addresses.add(createEntity(i));
		}

		return addresses;
	}
}
