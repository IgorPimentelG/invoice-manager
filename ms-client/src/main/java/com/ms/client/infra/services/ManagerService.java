package com.ms.client.infra.services;

import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.errors.BadRequestException;
import com.ms.client.infra.errors.NotFoundException;
import com.ms.client.infra.mappers.ManagerMapper;
import com.ms.client.infra.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
	private AccountService accountService;

	@Autowired
	private AuthorizationService authorizationService;

	@Autowired
	private NotificationService notificationService;

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
		var code = accountService.generateCode(entity);

		String content = String.format(
		  "Your account has been created. Please confirm your code to active your account. Code: %s",
		  code
		);
		notificationService.sendNotification(
		  entity.getEmail(),
		  content,
		  entity.getId()
		);

		logger.log(Level.INFO, "A new manager has been saved.");

		return entity;
	}

	public Manager update(Manager manager) {
		if (manager == null) {
			throw new BadRequestException("Data cannot be null.");
		}

		var entity = findById(manager.getId());
		authorizationService.isAuthorized(entity);

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
		authorizationService.isAuthorized(entity);

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
