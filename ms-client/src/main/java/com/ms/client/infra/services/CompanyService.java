package com.ms.client.infra.services;

import com.ms.client.domain.entities.Address;
import com.ms.client.domain.entities.Company;
import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.errors.BadRequestException;
import com.ms.client.infra.errors.NotFoundException;
import com.ms.client.infra.errors.UnauthorizedException;
import com.ms.client.infra.mappers.CompanyMapper;
import com.ms.client.infra.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository repository;

	@Autowired
	private AddressService addressService;

	@Autowired
	private ManagerService managerService;

	private final CompanyMapper mapper = CompanyMapper.INSTANCE;
	private final Logger logger = Logger.getLogger(CompanyService.class.getName());

	public Company create(Company company, Address address) {
		if (company == null || address == null) {
			throw new BadRequestException("Data cannot be null.");
		}

		var companyAddress = addressService.create(address);
		var currentManager = (Manager) getAuthentication().getPrincipal();
		var manager = managerService.findById(currentManager.getId());

		company.addAddress(companyAddress);
		company.setManager(manager);

		var entity = repository.save(company);

		logger.log(Level.INFO, "The company has been created.");

		return entity;
	}

	public Company update(Company company) {
		if (company == null) {
			throw new BadRequestException("Data cannot be null.");
		}

		var entity = findById(company.getId());

		mapper.map(company, entity);
		entity.setUpdatedAt(LocalDateTime.now());

		repository.save(entity);

		logger.log(Level.INFO, "The company has been updated.");

		return entity;
	}

	public Company addAddress(Address address, String companyId) {
		if (address == null) {
			throw new BadRequestException("Data cannot be null.");
		}

		var company = findById(companyId);
		var addressEntity = addressService.create(address);
		company.addAddress(addressEntity);

		repository.save(company);

		logger.log(Level.INFO, "The address has been added.");

		return company;
	}

	public Company transfer(String companyId, String managerId) {
		var company = findById(companyId);
		var manager = managerService.findById(managerId);

		isAuthorized(company.getManager());

		company.setManager(manager);
		repository.save(company);

		logger.log(Level.INFO, "The address has been transferred.");

		return company;
	}

	public Company findById(String id) {
		var entity = repository.findById(id)
		  .orElseThrow(() -> {
			  logger.log(Level.WARNING, "The company does not exist.");
			  return new NotFoundException("Company");
		  });

		logger.log(Level.INFO, "The company has been found.");

		return entity;
	}

	public List<Company> findAll() {
		var manager = (Manager) getAuthentication().getPrincipal();
		logger.log(Level.INFO, "All companies has been found.");
		return manager.getCompanies();
	}

	public void delete(String id) {
		var entity = findById(id);
		isAuthorized(entity.getManager());

		logger.log(Level.INFO, "The company has been deleted.");

		repository.delete(entity);
	}

	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	private void isAuthorized(Manager manager) {
		var autenticateManager = (Manager) getAuthentication().getPrincipal();

		if (!autenticateManager.equals(manager)) {
			logger.log(Level.INFO, "Manager is not authorized.");
			throw new UnauthorizedException();
		}
	}
}
