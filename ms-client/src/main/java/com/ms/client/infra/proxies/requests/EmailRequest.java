package com.ms.client.infra.proxies.requests;

public record EmailRequest(
  String to,
  String subject,
  String content,
  String ownerRef
) {}
