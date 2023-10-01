package com.ms.client.infra.services;

import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.dtos.AuthResponseDto;
import com.ms.client.infra.errors.*;
import com.ms.client.infra.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	public AuthResponseDto signIn(String email, String password) {
		try {
			var manager = new UsernamePasswordAuthenticationToken(email, password);

			authenticationManager.authenticate(manager);

			var authenticatedManager = managerRepository.findByEmail(email)
			  .orElseThrow(() -> new NotFoundException("Manager"));

			if (!authenticatedManager.isEnabled()) {
				throw new UnauthorizedException();
			}

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

	public void recoverAccount(String email) {

		var manager = managerRepository.findByEmail(email)
		  .orElseThrow(() -> new NotFoundException("Manager"));

		var code = accountService.generateCode(manager);

		String content = String.format("Confirm code %s to continue.", code);
		notificationService.sendNotification(
		  manager.getEmail(),
		  content,
		  manager.getId()
		);
	}

	public void changePassword(String email, String code, String password, String passwordConfirmation) {

		if (!password.equals(passwordConfirmation)) {
			throw new BadRequestException("The passwords are different.");
		}

		var manager = managerRepository.findByEmail(email)
		  .orElseThrow(() -> new NotFoundException("Manager"));

		if (!manager.isEnabled()) {
			throw new UnauthorizedException();
		}

		accountService.verifyCode(manager.getId(), code);

		var passwordEncrypted = encoder.encode(password);
		manager.setPassword(passwordEncrypted);

		managerRepository.save(manager);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return managerRepository.findByEmail(username)
		  .orElseThrow(() -> new NotFoundException("Manager"));
	}
}
