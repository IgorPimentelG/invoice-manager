package com.ms.client.infra.services;

import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.errors.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	public void isAuthorized(Manager manager) {
		var autenticateManager = getAuthenticatedUser();

		if (!autenticateManager.equals(manager)) {
			throw new UnauthorizedException();
		}
	}

	public Manager getAuthenticatedUser() {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		return (Manager) auth.getPrincipal();
	}
}
