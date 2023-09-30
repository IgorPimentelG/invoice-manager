package com.ms.electronic.invoice.infra.dtos;

public record ValidationDto(String number, String issuer, String recipient, boolean isValid) {}
