package com.ms.electronic.invoice.infra.dtos;

public record EmailRequestDto(
  String to,
  String subject,
  String content,
  String ownerRef
) {}
