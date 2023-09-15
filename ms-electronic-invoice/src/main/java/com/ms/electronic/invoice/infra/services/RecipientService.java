package com.ms.electronic.invoice.infra.services;

import com.ms.electronic.invoice.domain.entities.Address;
import com.ms.electronic.invoice.domain.entities.Invoice;
import com.ms.electronic.invoice.domain.entities.Issuer;
import com.ms.electronic.invoice.domain.entities.Recipient;
import com.ms.electronic.invoice.infra.errors.BadRequestException;
import com.ms.electronic.invoice.infra.errors.NotFoundException;
import com.ms.electronic.invoice.infra.repositories.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RecipientService {

	@Autowired
	private RecipientRepository repository;

	@Autowired
	private AddressService addressService;

	private final Logger logger = Logger.getLogger(RecipientService.class.getName());

	public Recipient create(Recipient recipient, Address address) {
		if (recipient == null) {
			throw new BadRequestException("Recipient cannot be null.");
		}

		var entity = repository.findById(recipient.getCnpj());

		if (entity.isEmpty()) {
			var entityAddress = addressService.create(address);
			recipient.setAddress(entityAddress);
			entity = Optional.of(repository.save(recipient));

			logger.log(Level.INFO, "Recipient has been created.");
		}

		return entity.get();
	}

	public Recipient findById(String cnpj) {
		var entity = repository.findById(cnpj)
		  .orElseThrow(() -> {
			  logger.log(Level.WARNING, String.format("Recipient %s not found", cnpj));
			  return new NotFoundException("Recipient not found.");
		  });

		logger.log(Level.INFO, String.format("Recipient %s was found.", cnpj));

		return entity;
	}

	public void addInvoice(Recipient recipient, Invoice invoice) {
		recipient.addInvoice(invoice);
		repository.save(recipient);
	}
}
