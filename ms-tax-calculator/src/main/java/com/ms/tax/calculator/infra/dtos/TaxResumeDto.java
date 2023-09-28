package com.ms.tax.calculator.infra.dtos;

import com.ms.tax.calculator.domain.entities.Tax;

import java.math.BigDecimal;
import java.util.List;

public record TaxResumeDto(List<Tax> taxes, String amount) {}
