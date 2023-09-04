package com.ms.client.infra.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record CreateManagerDto(
  @NotEmpty(message = "CPF is required")
  @CPF(message = "CPF is invalid. The allowed format is xxx.xxx.xxx-xx")
  String cpf,

  @NotEmpty(message = "Full name is required")
  @Length(min = 10, max = 100, message = "Full name needs a min of 10 characters and max of 100")
  String fullName,

  @Past(message = "Born date is invalid")
  LocalDate bornDate,

  @NotEmpty(message = "Phone is required")
  @Pattern(
	regexp = "^\\(\\d\\d\\)\\s(\\d{5})-(\\d{4})$",
    message = "Phone is invalid. The allowed format is (xx) xxxxx-xxxx"
  )
  String phone,

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
