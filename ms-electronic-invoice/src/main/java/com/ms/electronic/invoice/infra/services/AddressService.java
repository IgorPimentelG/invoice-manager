package com.ms.electronic.invoice.infra.services;

import com.ms.electronic.invoice.domain.entities.Address;
import com.ms.electronic.invoice.infra.errors.BadRequestException;
import com.ms.electronic.invoice.infra.errors.NotFoundException;
import com.ms.electronic.invoice.infra.mappers.AddressMapper;
import com.ms.electronic.invoice.infra.repositories.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repository;

	private final AddressMapper mapper = AddressMapper.INSTANCE;
	private final Logger logger = LoggerFactory.getLogger(AddressService.class);

	public Address create(Address address) {
		if (address == null) {
			throw new BadRequestException("Address data cannot be null.");
		}

		var entity = repository.save(address);

		logger.info("The address with id: {}, has been created.", address.getId());

		return entity;
	}

	public Address update(Address address) {
		if (address == null) {
			throw new BadRequestException("Address data cannot be null.");
		}

		var entity = findById(address.getId());

		mapper.map(address, entity);
		repository.save(entity);

		logger.info("The address with id: {}, has been updated.", entity.getId());

		return entity;
	}

	public Address findById(long id) {
		var entity = repository.findById(id)
		  .orElseThrow(() -> {
			  logger.warn("The Address with id: {}, not found.", id);
			  return  new NotFoundException("Address not found.");
		  });

		logger.info("The Address with id: {} was found.", id);

		return entity;
	}

	public void delete(long id) {
		var entity = findById(id);
		logger.info("The address with id: {}, has been deleted.", id);
		repository.delete(entity);
	}
}
