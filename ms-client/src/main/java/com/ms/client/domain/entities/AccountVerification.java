package com.ms.client.domain.entities;

import com.ms.client.domain.helpers.CodeGenerator;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "account_verification")
public class AccountVerification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private final String code;
	private boolean checked;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "checked_at")
	private LocalDateTime checkedAt;

	@Column(name = "expires_at")
	private LocalDateTime expiresAt;

	@ManyToOne
	@JoinColumn(name = "manager_id")
	private Manager manager;

	public AccountVerification() {
		this.checked = false;
		this.code = CodeGenerator.getCode(6);

		init();
	}

	public AccountVerification(Manager manager) {
		this.manager = manager;

		this.checked = false;
		this.code = CodeGenerator.getCode(6);

		init();
	}

	private void init() {
		this.createdAt = LocalDateTime.now();
		this.expiresAt = LocalDateTime.now().plusHours(1);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getCheckedAt() {
		return checkedAt;
	}

	public void setCheckedAt(LocalDateTime checkedAt) {
		this.checkedAt = checkedAt;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AccountVerification that = (AccountVerification) o;
		return id == that.id && Objects.equals(code, that.code);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, code);
	}
}
