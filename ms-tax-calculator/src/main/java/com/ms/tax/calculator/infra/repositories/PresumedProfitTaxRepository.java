package com.ms.tax.calculator.infra.repositories;

import com.ms.tax.calculator.domain.entities.PresumedProfitTax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresumedProfitTaxRepository extends JpaRepository<PresumedProfitTax, String> {}
