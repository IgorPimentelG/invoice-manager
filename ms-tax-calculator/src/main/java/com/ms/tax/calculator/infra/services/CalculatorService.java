package com.ms.tax.calculator.infra.services;

import com.ms.tax.calculator.domain.entities.NationalSimpleTax;
import com.ms.tax.calculator.domain.entities.PresumedProfitTax;
import com.ms.tax.calculator.domain.entities.Tax;
import com.ms.tax.calculator.infra.dtos.TaxResumeDto;
import com.ms.tax.calculator.infra.proxies.InvoiceProxy;
import com.ms.tax.calculator.infra.proxies.responses.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CalculatorService {

	@Autowired
	private InvoiceProxy invoiceProxy;

	public TaxResumeDto calculate(String cnpj) {
		List<Invoice> invoices = invoiceProxy.listAll(cnpj);
		List<Tax> taxes = new ArrayList<>();
		var currentReference = getCurrentReference();

		invoices.stream()
		  .filter(invoice -> invoice.reference().equals(currentReference))
		  .forEach(invoice -> {
			  Tax tax = switch (invoice.issuer().taxRegime()) {
				  case SIMPLE_NATIONAL -> nationalSimpleTaxes(invoice);
				  case PRESUMED_PROFIT -> presumedProfitTaxes(invoice);
			  };
			  taxes.add(tax);
		  });

		var amount = taxes.stream()
		  .map(Tax::getTaxAmount)
		  .reduce(BigDecimal.ZERO, BigDecimal::add);

		return new TaxResumeDto(taxes, formatAmount(amount));
	}

	/**
	 * Basis of calculation
	 * COMMERCE ----------------- 6%
	 * INDUSTRY ----------------- 8.5%
	 * SERVICE_PROVISION -------- 11%
	 */
	private NationalSimpleTax nationalSimpleTaxes(Invoice invoice) {
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
		  "SIMPLE NATIONAL",
		  invoice.amount(),
		  total,
		  aliquot,
		  invoice.type().toString().replaceAll("_", " ")
		);
	}

	/**
	 * Basis of calculation
	 * IRPJ ---------- 4,8%
	 * ISS ----------- 2%
	 * COFINS -------- 3%
	 */
	private PresumedProfitTax presumedProfitTaxes(Invoice invoice) {
		var irpj = getTax(4.8F, invoice.amount());
		var iss = getTax(2F, invoice.amount());
		var confis = getTax(3F, invoice.amount());

		var total = irpj.add(iss).add(confis);

		return new PresumedProfitTax(
		  invoice.number(),
		  "PRESUMED PROFIT",
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

	private BigDecimal getTax(float percentage, BigDecimal amount) {
		return amount.divide(BigDecimal.valueOf(100), RoundingMode.UP)
		  .multiply(BigDecimal.valueOf(percentage));
	}

	private String getCurrentReference() {
		var now = LocalDate.now();
		var monthValue = now.getMonthValue();
		var month = monthValue < 10 ? "0" + monthValue : String.valueOf(monthValue);
		return month + "/" + now.getYear();
	}

	private String formatAmount(BigDecimal amount) {
		var formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		return formatter.format(amount);
	}
}
