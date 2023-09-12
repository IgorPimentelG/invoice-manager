package com.ms.client.infra.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordDto(

  @NotEmpty(message = "Password is required")
  @Pattern(
	regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$",
	message = "Password needs to have at least eight characters, at least one uppercase letter, " +
	  "at least one lowercase letter, at least one number, and at least one special character."
  )
  String password,

  @NotEmpty(message = "Password confirmation is required")
  @Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$",
    message = "Password needs to have at least eight characters, at least one uppercase letter, " +
	  "at least one lowercase letter, at least one number, and at least one special character."
  )
  String passwordConfirmation
) {}
