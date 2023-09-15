package com.ms.electronic.invoice.infra.services;

import com.ms.electronic.invoice.domain.entities.Address;
import com.ms.electronic.invoice.domain.entities.Invoice;
import com.ms.electronic.invoice.domain.entities.Issuer;
import com.ms.electronic.invoice.infra.errors.BadRequestException;
import com.ms.electronic.invoice.infra.errors.NotFoundException;
import com.ms.electronic.invoice.infra.repositories.IssuerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class IssuerService {

	@Autowired
	private IssuerRepository repository;

	@Autowired
	private AddressService addressService;

	private final Logger logger = Logger.getLogger(IssuerService.class.getName());

	public Issuer create(Issuer issuer, Address address) {
		if (issuer == null) {
			throw new BadRequestException("Issuer cannot be null.");
		}

		var entity = repository.findById(issuer.getCnpj());

		if (entity.isEmpty()) {
			var entityAddress = addressService.create(address);
			issuer.setAddress(entityAddress);
			entity = Optional.of(repository.save(issuer));

			logger.log(Level.INFO, "Issuer has been created.");
		}

		return entity.get();
	}

	public Issuer findById(String cnpj) {
		var entity = repository.findById(cnpj)
		  .orElseThrow(() -> {
			  logger.log(Level.WARNING, String.format("Issuer %s not found", cnpj));
			  return new NotFoundException("Issuer not found.");
		  });

		logger.log(Level.INFO, String.format("Issuer %s was found.", cnpj));

		return entity;
	}

	public void addInvoice(Issuer issuer, Invoice invoice) {
		issuer.addInvoice(invoice);
		repository.save(issuer);
	}
}
