package com.ms.client.infra.services;

import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.errors.BadRequestException;
import com.ms.client.infra.errors.NotFoundException;
import com.ms.client.infra.errors.UnauthorizedException;
import com.ms.client.infra.mappers.ManagerMapper;
import com.ms.client.infra.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ManagerService {

	@Autowired
	private ManagerRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private AuthService authService;

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

		var entity = findById(manager.getId());
		authService.isAuthorized(entity);

		mapper.map(manager, entity);
		entity.setUpdatedAt(LocalDateTime.now());

		repository.save(entity);

		logger.log(Level.INFO, "A manager has been updated.");

		return entity;
	}

	public Manager findById(String id) {
		var entity = repository.findById(id)
		  .orElseThrow(() -> {
			  logger.log(Level.WARNING, "The manager does not exist.");
			  return new NotFoundException("Manager");
		  });

		logger.log(Level.INFO, "The manager has been found.");

		return entity;
	}

	public Manager disable(String id) {
		var entity = findById(id);
		authService.isAuthorized(entity);

		entity.disable();

		notificationService.sendNotification(
		  entity.getEmail(),
		  "Your account has been disabled",
		  entity.getId()
		);

		logger.log(Level.INFO, "A manager has been disabled.");

		return repository.save(entity);
	}
}
