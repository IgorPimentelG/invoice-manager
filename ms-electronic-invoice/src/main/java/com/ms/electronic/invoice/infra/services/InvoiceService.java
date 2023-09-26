package com.ms.electronic.invoice.infra.services;

import com.ms.electronic.invoice.domain.entities.Invoice;
import com.ms.electronic.invoice.domain.entities.Issuer;
import com.ms.electronic.invoice.infra.errors.BadRequestException;
import com.ms.electronic.invoice.infra.errors.NotFoundException;
import com.ms.electronic.invoice.infra.proxies.CompanyProxy;
import com.ms.electronic.invoice.infra.proxies.responses.Company;
import com.ms.electronic.invoice.infra.repositories.InvoiceRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceRepository repository;

	@Autowired
	private IssuerService issuerService;

	@Autowired
	private RecipientService recipientService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CodeService codeService;

	@Autowired
	private CompanyProxy companyProxy;

	private final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

	@CircuitBreaker(name = "cb_ms-client")
	public Invoice create(
	  String issuerCnpj,
	  Invoice invoice,
	  String credentials
	) {
		if (invoice == null) {
			throw new BadRequestException("Invoice data cannot be null.");
		}

		try {
			var company = companyProxy.getCompany(issuerCnpj, credentials);
			var issuer = new Issuer(
			  company.cnpj(),
			  company.corporateName(),
			  company.address().get(0)
			);

			issuerService.create(issuer);
			recipientService.create(invoice.getRecipient());

			var invoiceNumber = codeService.generateCode();
			invoice.setNumber(invoiceNumber);
			invoice.setValidationCode(codeService.encryptCode(invoiceNumber));
			invoice.setIssuer(issuer);

			var entity = repository.save(invoice);

			notify(company, invoiceNumber);
			logger.info("The invoice {} has been created.", invoiceNumber);

			return entity;
		} catch (Exception e) {
			logger.error("Not possible create invoice.");
			throw new BadRequestException(e.getMessage());
		}
	}

	public Invoice findById(String number) {
		var entity = repository.findById(number)
		  .orElseThrow(() -> {
			  logger.info("Invoice {} not found.", number);
			  return new NotFoundException("Invoice not found.");
		  });

		logger.info("Invoice {}, was found.", number);

		return entity;
	}

	public List<Invoice> findByCNPJ(String cnpj) {
		var entities = repository.findAllByCnpj(cnpj);
		logger.info("Find all Invoices of company {}.", cnpj);
		return entities;
	}

	public void cancel(String number) {
		var entity = findById(number);
		entity.setCanceled(true);

		logger.info("Invoice {}, was canceled.", number);

		repository.save(entity);
	}

	private void notify(Company company, String invoiceNumber) {
		var content = new StringBuilder();
		content.append("An invoice was issued at ");
		content.append(LocalDateTime.now());
		content.append(". Invoice number: ");
		content.append(invoiceNumber);
		content.append(". Validation code: ");
		content.append(codeService.encryptCode(invoiceNumber));

		notificationService.sendNotification(
		  company.email(),
		  content.toString(),
		  company.id()
		);
	}
}