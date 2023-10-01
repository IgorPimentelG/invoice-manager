package com.ms.client.infra.controllers.docs.auth;

import com.ms.client.infra.dtos.AuthResponseDto;
import com.ms.client.infra.errors.*;
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
@Operation(summary = "Sign In", description = "Allow authenticate a manager", tags = {"Auth"})
@ApiResponses({
	@ApiResponse(responseCode = "200", description = "The manager has valid credentials", content = @Content(
	  schema = @Schema(implementation = AuthResponseDto.class)
	)),
    @ApiResponse(responseCode = "403", description = "The manager has invalid credentials", content = @Content(
	  schema = @Schema(implementation = UnauthorizedException.class)
    )),
    @ApiResponse(responseCode = "404", description = "The credentials do not belong to any manager", content = @Content(
	  schema = @Schema(implementation = NotFoundException.class)
    ))
})
public @interface ApiOperationSignIn {}
