package com.ms.client.infra.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record ManagerCredentials(

  @NotEmpty(message = "Email is required")
  @Email(message = "Email is invalid. The allowed format is exemple@exemple.com")
  String email,

  @NotEmpty(message = "Password is required")
  @Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$",
    message = "Password needs to have at least eight characters, at least one uppercase letter, " +
      "at least one lowercase letter, at least one number, and at least one special character."
  )
  String password
) {}
