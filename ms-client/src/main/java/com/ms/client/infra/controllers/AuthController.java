package com.ms.client.infra.controllers;

import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.controllers.docs.auth.ApiOperationRefresh;
import com.ms.client.infra.controllers.docs.auth.ApiOperationSignIn;
import com.ms.client.infra.controllers.docs.auth.ApiOperationSignUp;
import com.ms.client.infra.dtos.AuthResponseDto;
import com.ms.client.infra.dtos.CreateManagerDto;
import com.ms.client.infra.dtos.ManagerCredentials;
import com.ms.client.infra.mappers.ManagerMapper;
import com.ms.client.infra.services.AuthService;
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
	private AuthService service;

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
		return  ResponseEntity.ok(response);
	}
}
