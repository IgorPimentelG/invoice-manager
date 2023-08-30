package com.ms.email.infra.consumers.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailDto(
  @NotBlank(message = "Email owner must be informed.")
  String ownerRef,

  @Email(message = "The recipient of the email must be informed.")
  String to,

  @NotBlank(message = "Email needs a subject.")
  String subject,

  @NotBlank(message = "Email content must be filled in.")
  String content
) {}
