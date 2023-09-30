package com.ms.tax.calculator.infra.proxies.responses;

import com.ms.tax.calculator.domain.types.TaxRegime;

public record Issuer(String cnpj, String corporateName, TaxRegime taxRegime) {}
