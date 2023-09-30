package com.ms.tax.calculator.infra.proxies.responses;

import com.ms.tax.calculator.domain.types.CompanyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Invoice(
   String number,
   String validationCode,
   LocalDateTime dateIssue,
   String description,
   BigDecimal amount,
   String reference,
   CompanyType type,
   boolean isCanceled,
   Issuer issuer
) {}
