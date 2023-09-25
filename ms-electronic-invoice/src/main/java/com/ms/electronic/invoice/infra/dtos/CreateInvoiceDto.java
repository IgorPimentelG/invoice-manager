package com.ms.electronic.invoice.infra.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record CreateInvoiceDto(

  @NotEmpty(message = "Description is required.")
  @Length(min = 10, max = 255, message = "The description must be between 10 and 255 characters.")
  String description,

  @Positive(message = "The amount must be positive.")
  BigDecimal amount,

  @Valid
  CreateRecipientDto recipient
) {}
