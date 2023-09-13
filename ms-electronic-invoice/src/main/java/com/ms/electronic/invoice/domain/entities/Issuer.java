package com.ms.electronic.invoice.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms.electronic.invoice.domain.validation.CompanyValidator;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "issuers")
public class Issuer {

	@Id
	private String cnpj;

	@Column(name = "corporate_name")
	private String corporateName;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "issuer_address",
		joinColumns = {@JoinColumn(name = "issuer_id")},
	    inverseJoinColumns = {@JoinColumn(name = "address_id")}
	)
	private Address address;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "issuer", cascade = CascadeType.ALL)
	private final List<Invoice> invoices;

	public Issuer() {
		this.invoices = new ArrayList<>();
	}

	public Issuer(String CNPJ, String corporateName, Address address) {
		this.cnpj = CNPJ;
		this.corporateName = corporateName;
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

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		CompanyValidator.validate(corporateName)
		  .isEmpty("Corporate name cannot be empty");
		this.corporateName = corporateName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Issuer issuer = (Issuer) o;
		return Objects.equals(cnpj, issuer.cnpj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cnpj);
	}
}