package com.ms.electronic.invoice.infra.repositories;

import com.ms.electronic.invoice.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {}
