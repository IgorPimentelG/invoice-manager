package com.ms.client.infra.services;

import com.ms.client.domain.entities.Address;
import com.ms.client.infra.errors.BadRequestException;
import com.ms.client.infra.mocks.MockAddress;
import com.ms.client.infra.repositories.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddressServiceTest {

	MockAddress mock;

	@InjectMocks
	AddressService service;

	@Mock
	AddressRepository repository;

	@BeforeEach
	void setup() {
		mock = new MockAddress();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("should create an address")
	void testCreateAddress() {
		Address address = mock.createEntity();

		when(repository.save(any())).thenReturn(address);

		var result = service.create(address);

		verify(repository, times(1)).save(any());
		assertNotNull(result);
		assertEquals(address.getId(), result.getId());
		assertEquals(address.getCity(), result.getCity());
		assertEquals(address.getNumber(), result.getNumber());
		assertEquals(address.getState(), result.getState());
		assertEquals(address.getNeighborhood(), result.getNeighborhood());
		assertEquals(address.getZipCode(), result.getZipCode());
	}

	@Test
	@DisplayName("should throws BadRequestException when create an address with null data")
	void testCreateAddressWithNullData() {
		Exception exception = assertThrows(BadRequestException.class, () -> {
			service.create(null);
		});

		String expectedMessage = "Address cannot be null.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).save(any());
		assertEquals(expectedMessage, resultMessage);
	}
}
