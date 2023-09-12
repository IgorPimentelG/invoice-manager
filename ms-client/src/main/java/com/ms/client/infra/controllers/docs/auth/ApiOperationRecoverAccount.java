package com.ms.client.infra.controllers.docs.auth;

import com.ms.client.infra.errors.NotFoundException;
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
@Operation(summary = "Recover account", description = "Allow request account recovery", tags = {"Auth"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "The request has accepted", content = @Content(
	schema = @Schema(implementation = String.class)
  )),
  @ApiResponse(responseCode = "404", description = "The account was not found.", content = @Content(
	schema = @Schema(implementation = NotFoundException.class)
  ))
})
public @interface ApiOperationRecoverAccount {}
