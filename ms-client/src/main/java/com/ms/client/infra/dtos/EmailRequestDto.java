package com.ms.client.infra.dtos;

public record EmailRequestDto(
  String to,
  String subject,
  String content,
  String ownerRef
) {}
