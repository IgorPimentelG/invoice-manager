package com.ms.service.auth.infra.controllers;

import com.ms.service.auth.infra.controllers.docs.ApiOperationValidateToken;
import com.ms.service.auth.infra.dtos.TokenDto;
import com.ms.service.auth.infra.services.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Endpoints for valid authentication")
public class AuthController {

	@Autowired
	private TokenService service;

	@GetMapping("/token/validate")
	@ApiOperationValidateToken
	public ResponseEntity<TokenDto> validateToken(@RequestParam("accessToken") String accessToken) {
		var result = service.validateToken(accessToken);
		return ResponseEntity.ok(result);
	}
}
