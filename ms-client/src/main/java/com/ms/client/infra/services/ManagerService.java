package com.ms.client.infra.services;

import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.errors.BadRequestException;
import com.ms.client.infra.errors.NotFoundException;
import com.ms.client.infra.mappers.ManagerMapper;
import com.ms.client.infra.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ManagerService {

	@Autowired
	private ManagerRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	private final ManagerMapper mapper = ManagerMapper.INSTANCE;
	private final Logger logger = Logger.getLogger(ManagerService.class.getName());

	public Manager create(Manager manager) {

		if (manager == null) {
			throw new BadRequestException("Data cannot be null.");
		}

		manager.setPassword(
		  encoder.encode(manager.getPassword())
		);
		var entity = repository.save(manager);

		logger.log(Level.INFO, "A new manager has been saved.");

		return entity;
	}

	public Manager update(Manager manager) {

		if (manager == null) {
			throw new BadRequestException("Data cannot be null.");
		}

		var entity = repository.findById(manager.getId())
		  .orElseThrow(() -> new NotFoundException("Manager"));

		mapper.map(manager, entity);
		repository.save(entity);

		logger.log(Level.INFO, "A address has been updated.");

		return entity;
	}

	public Manager findById(String id) {
		var entity = repository.findById(id)
		  .orElseThrow(() -> {
			  logger.log(Level.WARNING, "The address does not exist.");
			  return new NotFoundException("Manager");
		  });

		logger.log(Level.INFO, "The manager has been found.");

		return entity;
	}
}
