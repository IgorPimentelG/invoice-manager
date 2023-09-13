package com.ms.electronic.invoice.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms.electronic.invoice.domain.validation.CompanyValidator;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "recipients")
public class Recipient {

	@Id
	private String cnpj;

	@Column(name = "state_registration")
	private String stateRegistration;

	@Column(name = "corporate_name")
	private String corporateName;

	private String phone;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "recipient_address",
		joinColumns = {@JoinColumn(name = "recipient_id")},
	    inverseJoinColumns = {@JoinColumn(name = "address_id")}
	)
	private Address address;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "recipient", cascade = CascadeType.ALL)
	private final List<Invoice> invoices;

	public Recipient() {
		this.invoices = new ArrayList<>();
	}

	public Recipient(
	  String CNPJ,
	  String stateRegistration,
	  String corporateName,
	  String phone,
	  Address address
	) {
		this.cnpj = CNPJ;
		this.stateRegistration = stateRegistration;
		this.corporateName = corporateName;
		this.phone = phone;
		this.address = address;

		this.invoices = new ArrayList<>();
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		CompanyValidator.validate(cnpj)
		  .isEmpty("CNPJ cannot be empty.")
		  .isCnpj();
		this.cnpj = cnpj;
	}

	public String getStateRegistration() {
		return stateRegistration;
	}

	public void setStateRegistration(String stateRegistration) {
		CompanyValidator.validate(stateRegistration)
		  .isEmpty("State registration cannot be empty.")
		  .isStateRegistration();
		this.stateRegistration = stateRegistration;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		CompanyValidator.validate(corporateName)
		  .isEmpty("Corporate name cannot be empty.");
		this.corporateName = corporateName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		CompanyValidator.validate(phone)
		  .isEmpty("Phone cannot be empty.")
		  .isPhone();
		this.phone = phone;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void addInvoice(Invoice invoice) {
		this.invoices.add(invoice);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Recipient recipient = (Recipient) o;
		return Objects.equals(cnpj, recipient.cnpj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj);
	}
}
