package com.ms.client.infra.dtos;

import jakarta.validation.constraints.*;

public class UpdateManagerDto{
	@Pattern(
	  regexp = "^\\(\\d\\d\\)\\s(\\d{5})-(\\d{4})$",
	  message = "Phone is invalid. The allowed format is (xx) xxxxx-xxxx"
	)
	private String phone;

	@Pattern(
	  regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$",
	  message = "Password needs to have at least eight characters, at least one uppercase letter, " +
		"at least one lowercase letter, at least one number, and at least one special character."
	)
	private String password;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
