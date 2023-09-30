package com.ms.tax.calculator.infra.services;

import com.ms.tax.calculator.domain.entities.*;
import com.ms.tax.calculator.domain.types.CompanyType;
import com.ms.tax.calculator.infra.errors.*;
import com.ms.tax.calculator.infra.proxies.InvoiceProxy;
import com.ms.tax.calculator.infra.proxies.responses.Invoice;
import com.ms.tax.calculator.infra.proxies.responses.User;
import com.ms.tax.calculator.infra.repositories.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.getOwner;

@Service
public class CalculatorService {

	@Autowired
	private InvoiceProxy invoiceProxy;

	@Autowired
	private TaxResumeRepository repository;

	@Autowired
	private NationalSimpleTaxRepository nationalSimpleTaxRepository;

	@Autowired
	private PresumedProfitTaxRepository presumedProfitTaxRepository;

	private final Logger logger = LoggerFactory.getLogger(CalculatorService.class);

	@CircuitBreaker(name = "cb_ms-electronic-invoice")
	public TaxResume calculate(String cnpj) {
		var currentReference = getCurrentReference();
		var resumes = repository.findByReference(currentReference);

		if (resumes.isEmpty()) {
			List<Invoice> invoices = invoiceProxy.listAll(cnpj);
			var taxes = calculateTaxPerInvoice(invoices, currentReference);
			var amount = calculateTotalAmount(taxes);

			if (taxes.isEmpty()) {
				logger.warn("No taxes payable to {}.", cnpj);
				throw new NoTaxesPayableException();
			}

			var owner = getCurrentUser();
			var taxResume = new TaxResume(currentReference, amount, owner);
			taxes.forEach(tax -> tax.setResume(taxResume));
			taxResume.setTaxes(taxes);

			repository.save(taxResume);

			taxes.forEach(tax -> {
				if (tax instanceof NationalSimpleTax) {
					nationalSimpleTaxRepository.save((NationalSimpleTax) tax);
				} else {
					presumedProfitTaxRepository.save((PresumedProfitTax) tax);
				}
			});

			logger.info("Taxes for reference {} were generated.", currentReference);

			return taxResume;
		}
		return resumes.get();
	}

	public TaxResume pay(String id) {
		var entity = repository.findById(id)
		  .orElseThrow(() -> new NotFoundException("No such tax"));

		if (!entity.getOwner().equals(getCurrentUser())) {
			throw new UnauthorizedException();
		}

		entity.setPaid(true);
		repository.save(entity);

		return entity;
	}

	/**
	 * Basis of calculation
	 * COMMERCE ----------------- 6%
	 * INDUSTRY ----------------- 8.5%
	 * SERVICE_PROVISION -------- 11%
	 */
	private NationalSimpleTax calculateNationalSimpleTaxes(Invoice invoice) {
		var aliquot = "";
		var total = switch (invoice.type()) {
			case COMMERCE -> {
				aliquot = "6%";
				yield getTax(6F, invoice.amount());
			}
			case INDUSTRY -> {
				aliquot = "8.5%";
				yield getTax(8.5F, invoice.amount());
			}
			case SERVICE_PROVISION -> {
				aliquot = "11%";
				yield getTax(11F, invoice.amount());
			}
		};

		return new NationalSimpleTax(
		  invoice.number(),
		  "Simples Nacional",
		  invoice.amount(),
		  total,
		  aliquot,
		  translateType(invoice.type())
		);
	}

	/**
	 * Basis of calculation
	 * IRPJ ---------- 4,8%
	 * ISS ----------- 2%
	 * COFINS -------- 3%
	 */
	private PresumedProfitTax calculatePresumedProfitTaxes(Invoice invoice) {
		var irpj = getTax(4.8F, invoice.amount());
		var iss = getTax(2F, invoice.amount());
		var confis = getTax(3F, invoice.amount());

		var total = irpj.add(iss).add(confis);

		return new PresumedProfitTax(
		  invoice.number(),
		  "Lucro Presumido",
		  invoice.amount(),
		  total,
		  "4.8%",
		  "2%",
		  "3%",
		  irpj,
		  iss,
		  confis
		);
	}

	private BigDecimal calculateTotalAmount(List<Tax> taxes) {
		return taxes.stream()
		  .map(Tax::getTaxAmount)
		  .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private List<Tax> calculateTaxPerInvoice(List<Invoice> invoices, String currentReference) {
		List<Tax> taxes = new ArrayList<>();

		invoices.stream()
		  .filter(invoice -> invoice.reference().equals(currentReference))
		  .filter(invoice -> !invoice.isCanceled())
		  .forEach(invoice -> {
			  var tax = switch (invoice.issuer().taxRegime()) {
				  case SIMPLE_NATIONAL -> calculateNationalSimpleTaxes(invoice);
				  case PRESUMED_PROFIT -> calculatePresumedProfitTaxes(invoice);
			  };
			  taxes.add(tax);
		  });

		return taxes;
	}

	private BigDecimal getTax(float percentage, BigDecimal amount) {
		return amount.divide(BigDecimal.valueOf(100), RoundingMode.UP)
		  .multiply(BigDecimal.valueOf(percentage));
	}

	private String getCurrentReference() {
		var now = LocalDate.now();
		var monthValue = now.getMonthValue() - 1;
		var month = monthValue < 10 ? "0" + monthValue : String.valueOf(monthValue);
		return month + "/" + now.getYear();
	}

	private String getCurrentUser() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		var user = (User) auth.getPrincipal();
		return user.getId();
	}

	private String translateType(CompanyType type) {
		return switch (type) {
			case COMMERCE -> "Comércio";
			case INDUSTRY -> "Indústria";
			case SERVICE_PROVISION -> "Prestação de serviço";
		};
	}
}
