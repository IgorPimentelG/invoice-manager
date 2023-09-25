package com.ms.electronic.invoice.infra.repositories;

import com.ms.electronic.invoice.domain.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
	Optional<Invoice> findByNumber(String number);

	@Query("SELECT i FROM Invoice i INNER JOIN i.issuer issuer WHERE issuer.cnpj = :cnpj")
	List<Invoice> findAllByCnpj(@Param("cnpj") String cnpj);
}
