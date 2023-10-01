package com.ms.client.infra.controllers;

import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.controllers.docs.auth.*;
import com.ms.client.infra.dtos.*;
import com.ms.client.infra.mappers.ManagerMapper;
import com.ms.client.infra.services.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Endpoints for authentication")
public class AuthController {

	@Autowired
	private AuthenticationService service;

	@Autowired
	private AccountService accountService;

	private final ManagerMapper mapper = ManagerMapper.INSTANCE;

	@ApiOperationSignIn
	@PostMapping("/sign-in")
	public ResponseEntity<AuthResponseDto> signIn(@RequestBody @Valid ManagerCredentials credentials) {
		var response = service.signIn(credentials.email(), credentials.password());
		return ResponseEntity.ok(response);
	}

	@ApiOperationSignUp
	@PostMapping("/sign-up")
	public ResponseEntity<Manager> signUp(@RequestBody @Valid CreateManagerDto managerDto) {
		var manager = mapper.createManager(managerDto);
		var response = service.signUp(manager);
		return ResponseEntity.ok(response);
	}

	@ApiOperationRefresh
	@PutMapping("/refresh-token/{email}")
	public ResponseEntity<AuthResponseDto> refreshToken(
	  @PathVariable("email") String email,
	  @RequestHeader("Authorization") String refreshToken
	) {
		var response = service.refreshToken(email, refreshToken);
		return ResponseEntity.ok(response);
	}

	@ApiOperationVerifyCode
	@PostMapping("/verify-code/{managerId}/{code}")
	public ResponseEntity<?> verifyCode(
	  @PathVariable("managerId") String managerId,
	  @PathVariable("code") String code
	) {
		accountService.verifyCode(managerId, code);
		return ResponseEntity.noContent().build();
	}

	@ApiOperationRecoverAccount
	@PostMapping("/recover-account/{email}")
	public ResponseEntity<String> recoverAccount(@PathVariable("email") String email) {
		service.recoverAccount(email);
		return ResponseEntity.ok("Verify your email.");
	}

	@ApiOperationChangePassword
	@PostMapping("/change-password/{email}/{code}")
	public ResponseEntity<String> changePassword(
	  @PathVariable("email") String email,
	  @PathVariable("code") String code,
	  @RequestBody @Valid ChangePasswordDto data) {
		service.changePassword(email, code, data.password(), data.passwordConfirmation());
		return ResponseEntity.ok("Your password has been changed.");
	}
}
