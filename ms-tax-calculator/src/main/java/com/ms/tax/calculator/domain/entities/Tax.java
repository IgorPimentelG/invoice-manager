package com.ms.tax.calculator.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Tax implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "invoice_number")
	private String invoiceNumber;

	private String type;

	@Column(name = "invoice_amount")
	private BigDecimal invoiceAmount;

	@Column(name = "tax_amount")
	private BigDecimal taxAmount;

	@ManyToOne
	@JoinColumn(name = "tax_resume_id")
	@JsonBackReference
	private TaxResume resume;

	public Tax(String invoiceNumber, String type, BigDecimal invoiceAmount, BigDecimal taxAmount) {
		this.invoiceNumber = invoiceNumber;
		this.type = type;
		this.invoiceAmount = invoiceAmount;
		this.taxAmount = taxAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public TaxResume getResume() {
		return resume;
	}

	public void setResume(TaxResume resume) {
		this.resume = resume;
	}
}
