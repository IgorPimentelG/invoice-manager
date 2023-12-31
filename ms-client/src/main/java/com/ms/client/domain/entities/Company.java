package com.ms.client.domain.entities;

import com.ms.client.domain.types.TaxRegime;
import com.ms.client.domain.validation.CompanyValidator;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "companies")
public class Company extends RepresentationModel<Company> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "corporate_name")
	private String corporateName;

	@Enumerated(EnumType.STRING)
	@Column(name = "tax_regime")
	private TaxRegime taxRegime;

	@Column(unique = true)
	private String cnpj;

	@Column(unique = true)
	private String email;

	private String phone;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "manager_id")
	private Manager manager;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "company_address",
	  joinColumns = {@JoinColumn(name = "id_company")},
	  inverseJoinColumns = {@JoinColumn(name = "id_address")}
	)
	private final List<Address> address;

	@Column(name = "created_at")
	private final LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public Company() {
		this.address = new ArrayList<>();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public Company(
	  String id,
	  String corporateName,
	  TaxRegime taxRegime,
	  String cnpj,
	  String email,
	  String phone,
	  Manager manager) {
		setId(id);
		setCorporateName(corporateName);
		setTaxRegime(taxRegime);
		setCnpj(cnpj);
		setEmail(email);
		setPhone(phone);
		setManager(manager);

		this.address = new ArrayList<>();
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		CompanyValidator.validate(id)
		  .isEmpty("ID must not be empty.")
		  .isIdentifier();
		this.id = id;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		CompanyValidator.validate(corporateName)
		  .isEmpty("Corporate name must not be empty.");
		this.corporateName = corporateName;
	}

	public TaxRegime getTaxRegime() {
		return taxRegime;
	}

	public void setTaxRegime(TaxRegime taxRegime) {
		this.taxRegime = taxRegime;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		CompanyValidator.validate(cnpj)
		  .isEmpty("CNPJ must not be empty.")
		  .isCnpj();
		this.cnpj = cnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		CompanyValidator.validate(email)
		  .isEmpty("Email must not be empty.")
		  .isEmail();
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		CompanyValidator.validate(phone)
		  .isEmpty("Phone must not be empty.")
		  .isPhone();
		this.phone = phone;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public Collection<Address> getAddress() {
		return Collections.unmodifiableCollection(address);
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void addAddress(Address address) {
		this.address.add(address);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Company company = (Company) o;
		return Objects.equals(id, company.id) && Objects.equals(cnpj, company.cnpj);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, cnpj);
	}
}
