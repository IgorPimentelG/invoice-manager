package com.ms.client.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms.client.domain.validation.ManagerValidator;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "managers")
public class Manager extends RepresentationModel<Manager> implements Serializable, UserDetails {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(unique = true)
	private String cpf;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "born_date")
	private LocalDate bornDate;

	private String phone;

	@Column(unique = true)
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Column(name = "enabled")
	private boolean isEnabled;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "account_non_expired")
	private boolean isAccountNonExpired;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "account_non_locked")
	private boolean isAccountNonLocked;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "credentials_non_expired")
	private boolean isCredentialsNonExpired;

	@Column(name = "created_at")
	private final LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "manager", cascade = CascadeType.ALL)
	private final List<Company> companies;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "manager", cascade = CascadeType.ALL)
	private final List<AccountVerification> accountVerifications;

	public Manager() {
		disable();
		this.companies = new ArrayList<>();
		this.accountVerifications = new ArrayList<>();
		this.createdAt = LocalDateTime.now();
	}

	public Manager(
	  String id,
	  String cpf,
	  String fullName,
	  LocalDate bornDate,
	  String phone,
	  String email,
	  String password) {
		setId(id);
		setCpf(cpf);
		setFullName(fullName);
		setBornDate(bornDate);
		setPhone(phone);
		setEmail(email);
		setPassword(password);

		disable();

		this.companies = new ArrayList<>();
		this.accountVerifications = new ArrayList<>();
		this.createdAt = LocalDateTime.now();
	}

	public void activateAccount() {
		this.isEnabled = true;
		this.isAccountNonExpired = true;
		this.isAccountNonLocked = true;
		this.isCredentialsNonExpired = true;
		this.updatedAt = LocalDateTime.now();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		ManagerValidator.validate(id)
		  .isEmpty("ID must not be empty.")
		  .isIdentifier();
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		ManagerValidator.validate(cpf)
		  .isEmpty("CPF must not be empty.")
		  .isCpf();
		this.cpf = cpf;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		ManagerValidator.validate(fullName)
		  .isEmpty("Full name must not be empty.");
		this.fullName = fullName;
	}

	public LocalDate getBornDate() {
		return bornDate;
	}

	public void setBornDate(LocalDate bornDate) {
		ManagerValidator.validate(bornDate.toString())
		  .isBornDate()
		  .isLegalAge();
		this.bornDate = bornDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		ManagerValidator.validate(phone)
		  .isEmpty("Phone must not be empty.")
		  .isPhone();
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		ManagerValidator.validate(email)
		  .isEmpty("Email must not be empty.")
		  .isEmail();
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		ManagerValidator.validate(password)
		  .isEmpty("Password must not be empty.")
		  .isPassword();
		this.password = password;
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

	public List<Company> getCompanies() {
		return companies;
	}

	public void addCompany(Company company) {
		this.companies.add(company);
	}

	public void addAccountVerification(AccountVerification accountVerification) {
		this.accountVerifications.add(accountVerification);
	}

	public List<AccountVerification> getAccountVerifications() {
		return accountVerifications;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public void disable() {
		this.isEnabled = false;
		this.isAccountNonLocked = false;
		this.isAccountNonExpired = false;
		this.isCredentialsNonExpired = false;

		setUpdatedAt(LocalDateTime.now());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Manager manager = (Manager) o;
		return Objects.equals(id, manager.id) && Objects.equals(cpf, manager.cpf);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, cpf);
	}
}
