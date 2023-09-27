package com.ms.electronic.invoice.infra.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record CreateInvoiceDto(

  @NotEmpty(message = "Description is required.")
  @Length(min = 10, max = 255, message = "The description must be between 10 and 255 characters.")
  String description,

  @Positive(message = "The amount must be positive.")
  BigDecimal amount,

  @NotEmpty(message = "Reference is required.")
  @Pattern(
    regexp = "^\\d{2}/\\d{4}$",
    message = "Reference must be in the format: xx/xxxx."
  )
  String reference,

  @NotEmpty(message = "Type is required.")
  @Pattern(
    regexp = "^(COMMERCE|INDUSTRY|SERVICE_PROVISION)$",
    message = "Allowed regimes: COMMERCE, INDUSTRY or SERVICE_PROVISION"
  )
  String type,

  @Valid
  CreateRecipientDto recipient
) {}
