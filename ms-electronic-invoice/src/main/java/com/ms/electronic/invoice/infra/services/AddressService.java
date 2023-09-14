package com.ms.electronic.invoice.infra.services;

import com.ms.electronic.invoice.domain.entities.Address;
import com.ms.electronic.invoice.infra.errors.BadRequestException;
import com.ms.electronic.invoice.infra.errors.NotFoundException;
import com.ms.electronic.invoice.infra.mappers.AddressMapper;
import com.ms.electronic.invoice.infra.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repository;

	private final AddressMapper mapper = AddressMapper.INSTANCE;
	private final Logger logger = Logger.getLogger(AddressService.class.getName());

	public Address create(Address address) {
		if (address == null) {
			throw new BadRequestException("Address data cannot be null.");
		}

		var entity = repository.save(address);

		logger.log(Level.INFO, "A new address has been created.");

		return entity;
	}

	public Address update(Address address) {
		if (address == null) {
			throw new BadRequestException("Address data cannot be null.");
		}

		var entity = findById(address.getId());

		mapper.map(address, entity);
		repository.save(entity);

		logger.log(Level.INFO, "An address has been updated.");

		return entity;
	}

	public Address findById(long id) {
		var entity = repository.findById(id)
		  .orElseThrow(() -> {
			  logger.log(Level.WARNING, String.format("Address with id: %d not found.", id));
			  return  new NotFoundException("Address not found.");
		  });

		logger.log(Level.INFO, String.format("Address with id: %d was found.", id));

		return entity;
	}

	public void delete(long id) {
		var entity = findById(id);

		logger.log(Level.INFO, "An address has been deleted.");

		repository.delete(entity);
	}
}
