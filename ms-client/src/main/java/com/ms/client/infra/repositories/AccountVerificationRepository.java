package com.ms.client.infra.repositories;

import com.ms.client.domain.entities.AccountVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountVerificationRepository extends JpaRepository<AccountVerification, Long> {
	Optional<AccountVerification> findByManagerId(String managerId);
}
