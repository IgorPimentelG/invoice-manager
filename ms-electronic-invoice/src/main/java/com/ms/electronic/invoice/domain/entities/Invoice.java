package com.ms.electronic.invoice.domain.entities;

import com.ms.electronic.invoice.domain.types.CompanyType;
import com.ms.electronic.invoice.domain.validation.InvoiceValidator;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "invoices")
public class Invoice extends RepresentationModel<Invoice> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	private String number;

	@Column(name = "validation_code")
	private String validationCode;

	@Column(name = "date_issue")
	private LocalDateTime dateIssue;

	private String description;

	private BigDecimal amount;

	private String reference;

	@Enumerated(EnumType.STRING)
	private CompanyType type;

	@Column(name = "canceled")
	private boolean isCanceled;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "recipient_id")
	private Recipient recipient;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "issuer_id")
	private Issuer issuer;

	public Invoice() {
		init();
	}

	public Invoice(
	  String number,
	  String validationCode,
	  String description,
	  BigDecimal value,
	  String reference,
	  CompanyType type,
	  Recipient recipient,
	  Issuer issuer
	) {
		setNumber(number);
		setValidationCode(validationCode);
		setDescription(description);
		setAmount(value);
		setReference(reference);
		setType(type);

		this.recipient = recipient;
		this.issuer = issuer;

		init();
	}

	public void init() {
		this.isCanceled = false;
		this.dateIssue = LocalDateTime.now();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		InvoiceValidator.validate(number)
		  .isEmpty("Invoice number cannot be empty")
		  .isOnlyNumber();
		this.number = number;
	}

	public String getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(String validationCode) {
		InvoiceValidator.validate(validationCode)
		  .isEmpty("Validation code cannot be empty.");
		this.validationCode = validationCode;
	}

	public LocalDateTime getDateIssue() {
		return dateIssue;
	}

	public void setDateIssue(LocalDateTime dateIssue) {
		InvoiceValidator.validate(dateIssue.toString())
		  .isFuture();
		this.dateIssue = dateIssue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		InvoiceValidator.validate(description)
		  .isEmpty("Description cannot be empty.");
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		InvoiceValidator.validate(String.valueOf(amount))
		  .isPositive();
		this.amount = amount;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		InvoiceValidator.validate(reference)
		  .isEmpty("Reference cannot be empty.")
		  .isReference();
		this.reference = reference;
	}

	public CompanyType getType() {
		return type;
	}

	public void setType(CompanyType type) {
		this.type = type;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public Issuer getIssuer() {
		return issuer;
	}

	public void setIssuer(Issuer issuer) {
		this.issuer = issuer;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public void setCanceled(boolean canceled) {
		isCanceled = canceled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Invoice invoice = (Invoice) o;
		return Objects.equals(number, invoice.number);
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}
}
