package com.ms.tax.calculator.domain.entities;

import java.math.BigDecimal;

public abstract class Tax {
	private String invoiceNumber;
	private String type;
	private BigDecimal invoiceAmount;
	private BigDecimal taxAmount;

	public Tax(String invoiceNumber, String type, BigDecimal invoiceAmount, BigDecimal taxAmount) {
		this.invoiceNumber = invoiceNumber;
		this.type = type;
		this.invoiceAmount = invoiceAmount;
		this.taxAmount = taxAmount;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}
}
