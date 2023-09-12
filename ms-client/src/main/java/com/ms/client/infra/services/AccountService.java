package com.ms.client.infra.services;

import com.ms.client.domain.entities.AccountVerification;
import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.errors.BadRequestException;
import com.ms.client.infra.errors.NotFoundException;
import com.ms.client.infra.repositories.AccountVerificationRepository;
import com.ms.client.infra.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountService {

	@Autowired
	private AccountVerificationRepository repository;

	@Autowired
	private ManagerRepository managerRepository;

	public String generateCode(Manager manager) {
		var verification = new AccountVerification(manager);
		manager.addAccountVerification(verification);

		repository.save(verification);

		return verification.getCode();
	}

	public void verifyCode(String managerId, String code) {
		var verifications = repository.findByManagerId(managerId)
		  .orElseThrow(() -> new NotFoundException("Verification not found."));

		var verification = verifications.get(verifications.size() - 1);

		if (!verification.getCode().equals(code) || verification.isChecked()) {
			throw new BadRequestException("The code is invalid.");
		} else if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new BadRequestException("The code is expired.");
		}

		verification.setChecked(true);
		verification.setCheckedAt(LocalDateTime.now());
		verification.getManager().activateAccount();

		repository.save(verification);
		managerRepository.save(verification.getManager());
	}
}
