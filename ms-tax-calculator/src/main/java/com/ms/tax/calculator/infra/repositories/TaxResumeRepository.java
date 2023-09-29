package com.ms.tax.calculator.infra.repositories;

import com.ms.tax.calculator.domain.entities.TaxResume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxResumeRepository extends JpaRepository<TaxResume, String> {
	Optional<TaxResume> findByReference(String reference);
}
