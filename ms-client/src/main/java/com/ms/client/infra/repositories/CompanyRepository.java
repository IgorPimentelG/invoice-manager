package com.ms.client.infra.repositories;

import com.ms.client.domain.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
	Optional<Company> findByCnpj(String cnpj);
}
