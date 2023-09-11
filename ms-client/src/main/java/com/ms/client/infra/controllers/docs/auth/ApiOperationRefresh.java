package com.ms.client.infra.controllers.docs.auth;

import com.ms.client.infra.dtos.AuthResponseDto;
import com.ms.client.infra.errors.UnauthorizedException;
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
@Operation(summary = "Refresh Token", description = "Allow update credentials", tags = {"Auth"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "The manager token has valid", content = @Content(
	schema = @Schema(implementation = AuthResponseDto.class)
  )),
  @ApiResponse(responseCode = "403", description = "The manager has invalid credentials", content = @Content(
	schema = @Schema(implementation = UnauthorizedException.class)
  ))
})
public @interface ApiOperationRefresh {}
