package com.ms.client.infra.services;

import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.dtos.AuthResponseDto;
import com.ms.client.infra.errors.NotFoundException;
import com.ms.client.infra.errors.UnauthorizedException;
import com.ms.client.infra.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	public AuthResponseDto signIn(String email, String password) {
		try {
			var manager = new UsernamePasswordAuthenticationToken(email, password);

			authenticationManager.authenticate(manager);

			var authenticatedManager = managerRepository.findByEmail(email)
			  .orElseThrow(() -> new NotFoundException("Manager"));
			var token = tokenService.createToken(authenticatedManager);

			return new AuthResponseDto(authenticatedManager, token);
		} catch (Exception ignored) {
			throw new UnauthorizedException();
		}
	}

	public Manager signUp(Manager manager) {
		return managerService.create(manager);
	}

	public AuthResponseDto refreshToken(String email, String refreshToken) {

		var manager = managerRepository.findByEmail(email)
		  .orElseThrow(() -> new NotFoundException("Manager"));
		var token = tokenService.refreshToken(refreshToken);

		return new AuthResponseDto(manager, token);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return managerRepository.findByEmail(username)
		  .orElseThrow(() -> new NotFoundException("Manager"));
	}
}
