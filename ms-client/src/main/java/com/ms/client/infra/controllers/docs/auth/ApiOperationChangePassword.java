package com.ms.client.infra.controllers.docs.auth;

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
@Operation(summary = "Change account password", description = "Allow set a new password", tags = {"Auth"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "The password was changed", content = @Content(
	schema = @Schema(implementation = String.class)
  )),
  @ApiResponse(responseCode = "400", description = "The data is invalid", content = @Content(
	schema = @Schema(implementation = BadRequestException.class)
  )),
  @ApiResponse(responseCode = "403", description = "The manager has invalid credentials", content = @Content(
	schema = @Schema(implementation = UnauthorizedException.class)
  )),
  @ApiResponse(responseCode = "404", description = "The account was not found.", content = @Content(
	schema = @Schema(implementation = NotFoundException.class)
  ))
})
public @interface ApiOperationChangePassword {}
