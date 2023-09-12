package com.ms.client.infra.repositories;

import com.ms.client.domain.entities.AccountVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountVerificationRepository extends JpaRepository<AccountVerification, Long> {
	Optional<List<AccountVerification>> findByManagerId(String managerId);
}
