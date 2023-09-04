package com.ms.client.infra.services;

import com.ms.client.domain.entities.Address;
import com.ms.client.infra.errors.BadRequestException;
import com.ms.client.infra.errors.NotFoundException;
import com.ms.client.infra.mocks.MockAddress;
import com.ms.client.infra.repositories.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddressServiceTest {

	MockAddress mock;

	@InjectMocks
	AddressService service;

	@Mock
	AddressRepository repository;

	@Mock
	ModelMapper mapper;

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

	@Test
	@DisplayName("should delete an address")
	void testDeleteAddress() {
		when(repository.findById(any())).thenReturn(Optional.of(mock.createEntity()));

		service.delete(1L);

		verify(repository, times(1)).delete(any());
	}

	@Test
	@DisplayName("should throws NotFoundException when delete an address that does not exists")
	void testDeleteAddressThatDoesNotExists() {
		Exception exception = assertThrows(NotFoundException.class, () -> {
			service.delete(1L);
		});

		String expectedMessage = "Address not found.";
		String resultMessage = exception.getMessage();

		verify(repository, times(0)).delete(any());
		assertEquals(expectedMessage, resultMessage);
	}
}
