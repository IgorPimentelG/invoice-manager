package com.ms.tax.calculator.domain.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "tax_resumes")
public class TaxResume extends RepresentationModel<TaxResume> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "resume", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Tax> taxes;

	private String reference;

	private String amount;

	@Column(name = "paid")
	private boolean isPaid;

	private String owner;

	public TaxResume() {}

	public TaxResume(String reference, BigDecimal amount, String owner) {
		setReference(reference);
		setAmount(amount);
		setOwner(owner);

		this.isPaid = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<Tax> taxes) {
		this.taxes = taxes;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = formatAmount(amount);
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean paid) {
		isPaid = paid;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	private String formatAmount(BigDecimal amount) {
		var formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		return formatter.format(amount);
	}
}
