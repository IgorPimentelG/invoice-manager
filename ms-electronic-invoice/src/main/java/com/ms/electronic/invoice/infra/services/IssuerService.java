package com.ms.electronic.invoice.infra.services;

import com.ms.electronic.invoice.domain.entities.*;
import com.ms.electronic.invoice.infra.errors.*;
import com.ms.electronic.invoice.infra.repositories.IssuerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IssuerService {

	@Autowired
	private IssuerRepository repository;

	@Autowired
	private AddressService addressService;

	private final Logger logger = LoggerFactory.getLogger(IssuerService.class);

	public Issuer create(Issuer issuer) {
		if (issuer == null) {
			throw new BadRequestException("Issuer data cannot be null.");
		}

		var entity = repository.findById(issuer.getCnpj());

		if (entity.isEmpty()) {
			var entityAddress = addressService.create(issuer.getAddress());
			issuer.setAddress(entityAddress);
			entity = Optional.of(repository.save(issuer));

			logger.info("Issuer {} has been created.", entity.get().getCnpj());
		}
		return entity.get();
	}

	public Issuer findById(String cnpj) {
		var entity = repository.findById(cnpj)
		  .orElseThrow(() -> {
			  logger.info("Issuer {} not found", cnpj);
			  return new NotFoundException("Issuer not found.");
		  });

		logger.info("Issuer {} was found.", cnpj);

		return entity;
	}

	public void addInvoice(Issuer issuer, Invoice invoice) {
		issuer.addInvoice(invoice);
		repository.save(issuer);
	}
}
