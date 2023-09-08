package com.ms.client.infra.services;

import com.ms.client.domain.entities.Address;
import com.ms.client.infra.errors.BadRequestException;
import com.ms.client.infra.errors.NotFoundException;
import com.ms.client.infra.mappers.AddressMapper;
import com.ms.client.infra.repositories.AddressRepository;
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
			throw new BadRequestException("Data cannot be null.");
		}

		var entity = repository.save(address);

		logger.log(Level.INFO, "A new address has been saved.");

		return entity;
	}

	public Address update(Address address) {
		if (address == null) {
			throw new BadRequestException("Data cannot be null.");
		}

		var entity = repository.findById(address.getId())
		  .orElseThrow(() -> new NotFoundException("Address"));

		mapper.map(address, entity);
		repository.save(entity);

		logger.log(Level.INFO, "A address has been updated.");

		return entity;
	}

	public Address findById(long id) {
		 var entity = repository.findById(id)
		  .orElseThrow(() -> {
			  logger.log(Level.WARNING, "The address does not exist.");
			  return new NotFoundException("Address");
		  });

		 logger.log(Level.INFO, "The address has been found.");

		 return entity;
	}

	public void delete(long id) {
		var entity = repository.findById(id)
		  .orElseThrow(() -> new NotFoundException("Address"));

		repository.delete(entity);

		logger.log(Level.INFO, "A address has been deleted.");
	}
}
