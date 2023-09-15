package com.ms.electronic.invoice.infra.services;

import com.ms.electronic.invoice.domain.entities.Address;
import com.ms.electronic.invoice.domain.entities.Invoice;
import com.ms.electronic.invoice.domain.entities.Recipient;
import com.ms.electronic.invoice.infra.errors.BadRequestException;
import com.ms.electronic.invoice.infra.errors.NotFoundException;
import com.ms.electronic.invoice.infra.repositories.RecipientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipientService {

	@Autowired
	private RecipientRepository repository;

	@Autowired
	private AddressService addressService;

	private final Logger logger = LoggerFactory.getLogger(RecipientService.class);

	public Recipient create(Recipient recipient, Address address) {
		if (recipient == null) {
			throw new BadRequestException("Recipient data cannot be null.");
		}

		var entity = repository.findById(recipient.getCnpj());

		if (entity.isEmpty()) {
			var entityAddress = addressService.create(address);
			recipient.setAddress(entityAddress);
			entity = Optional.of(repository.save(recipient));

			logger.info("Recipient {} has been created.", entity.get().getCnpj());
		}
		return entity.get();
	}

	public Recipient findById(String cnpj) {
		var entity = repository.findById(cnpj)
		  .orElseThrow(() -> {
			  logger.info("Recipient {} not found", cnpj);
			  return new NotFoundException("Recipient not found.");
		  });

		logger.info("Recipient {} was found.", cnpj);

		return entity;
	}

	public void addInvoice(Recipient recipient, Invoice invoice) {
		recipient.addInvoice(invoice);
		repository.save(recipient);
	}
}
