package com.ms.electronic.invoice.infra.services;

import com.ms.electronic.invoice.domain.entities.Address;
import com.ms.electronic.invoice.domain.entities.Invoice;
import com.ms.electronic.invoice.domain.entities.Issuer;
import com.ms.electronic.invoice.domain.entities.Recipient;
import com.ms.electronic.invoice.infra.errors.BadRequestException;
import com.ms.electronic.invoice.infra.errors.NotFoundException;
import com.ms.electronic.invoice.infra.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository repository;

	@Autowired
	private IssuerService issuerService;

	@Autowired
	private RecipientService recipientService;

	@Autowired
	private CodeService codeService;

	private final Logger logger = Logger.getLogger(InvoiceService.class.getName());

	public Invoice create(
	  Invoice invoice,
	  Issuer issuer,
	  Recipient recipient,
	  Address issuerAddress,
	  Address recipientAddress
	) {
		if (invoice == null) {
			throw new BadRequestException("Invoice data cannot be null");
		}

		var issuerEntity = issuerService.create(issuer, issuerAddress);
		var recipientEntity = recipientService.create(recipient, recipientAddress);
		var invoiceNumber = codeService.generateCode();
		var invoiceValidationCode = codeService.encryptCode(invoiceNumber);

		invoice.setIssuer(issuerEntity);
		invoice.setRecipient(recipientEntity);
		invoice.setNumber(invoiceNumber);
		invoice.setValidationCode(invoiceValidationCode);

		var entity = repository.save(invoice);
		issuerService.addInvoice(issuer, invoice);
		recipientService.addInvoice(recipient, invoice);

		logger.log(Level.INFO, "The invoice has been created.");

		return entity;
	}

	public Invoice findById(String number) {
		var entity = repository.findById(number)
		  .orElseThrow(() -> {
			  logger.log(Level.WARNING, String.format("Invoice %s not found", number));
			  return new NotFoundException("Invoice not found.");
		  });

		logger.log(Level.INFO, String.format("Invoice %s was found", number));

		return entity;
	}

	public Invoice findByCNPJ(String cnpj) {
		return null;
	}
}