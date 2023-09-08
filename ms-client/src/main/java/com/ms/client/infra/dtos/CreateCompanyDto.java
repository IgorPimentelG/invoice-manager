package com.ms.client.infra.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

public record CreateCompanyDto(
  @NotEmpty(message = "Corporate name is required")
  @Length(min = 1, max = 60, message = "Corporate name needs a min of 1 characters and max of 60")
  String corporateName,

  @NotEmpty(message = "CNPJ is required")
  @CNPJ(message = "CNPJ is invalid. The allowed format is (xx) xxxxx-xxxx")
  String cnpj,

  @NotEmpty(message = "Tax regime is required")
  @Pattern(
	regexp = "^(SIMPLE_NATIONAL|PRESUMED_PROFIT)$",
    message = "Allowed regimes: SIMPLE_NATIONAL or PRESUMED_PROFIT"
  )
  String taxRegime,

  @NotEmpty(message = "Email is required")
  @Email(message = "Email is invalid. The allowed format is exemple@exemple.com")
  String email,

  @NotEmpty(message = "Phone is required")
  @Pattern(
    regexp = "^\\(\\d\\d\\)\\s(\\d{5})-(\\d{4})$",
    message = "Phone is invalid. The allowed format is (xx) xxxxx-xxxx"
  )
  String phone,

  @Valid
  CreateAddressDto address
) {}
