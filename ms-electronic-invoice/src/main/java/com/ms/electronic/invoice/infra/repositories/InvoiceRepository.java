package com.ms.electronic.invoice.infra.repositories;

import com.ms.electronic.invoice.domain.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {
	Optional<Invoice> findByNumber(String number);
}
