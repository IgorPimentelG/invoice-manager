package com.ms.client.infra.proxies.response;

public record EmailResponse(
  String id,
  String ownerRef,
  String from,
  String to,
  String subject,
  String content,
  String status,
  long createdAt
) {}
