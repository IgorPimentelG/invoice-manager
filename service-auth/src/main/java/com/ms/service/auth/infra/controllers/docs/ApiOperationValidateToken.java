package com.ms.service.auth.infra.controllers.docs;

import com.ms.service.auth.domain.entities.User;
import com.ms.service.auth.infra.errors.InvalidTokenException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Validate token", description = "Allow validate a JWT token", tags = {"Auth"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "The token was validated", content = @Content(
	schema = @Schema(implementation = User.class)
  )),
  @ApiResponse(responseCode = "403", description = "The token is invalid", content = @Content(
	schema = @Schema(implementation = InvalidTokenException.class)
  ))
})
public @interface ApiOperationValidateToken {}
