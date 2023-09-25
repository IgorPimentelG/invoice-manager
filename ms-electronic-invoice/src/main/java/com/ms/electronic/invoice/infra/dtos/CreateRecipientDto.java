package com.ms.electronic.invoice.infra.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

public record CreateRecipientDto(
  @NotEmpty(message = "CNPJ is required")
  @CNPJ(message = "CNPJ is invalid. The allowed format is xx.xxx.xxx/xxxx-xx.")
  String cnpj,

  @NotEmpty(message = "Corporate name is required")
  @Length(min = 1, max = 60, message = "Corporate name needs a min of 1 characters and max of 60")
  String corporateName,

  @NotEmpty(message = "State registration is required")
  @Pattern(
    regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\.\\d{3}$",
    message = "State registration is invalid. The allowed format is xxx.xxx.xxx.xxx"
  )
  String stateRegistration,

  @NotEmpty(message = "Phone is required")
  @Pattern(
    regexp = "^\\(\\d\\d\\)\\s(\\d{5})-(\\d{4})$",
    message = "Phone is invalid. The allowed format is (xx) xxxxx-xxxx"
  )
  String phone,

  @Valid
  CreateAddressDto address
){}
