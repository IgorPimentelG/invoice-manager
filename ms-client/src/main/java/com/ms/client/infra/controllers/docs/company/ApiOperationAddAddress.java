package com.ms.client.infra.controllers.docs.company;

import com.ms.client.domain.entities.Company;
import com.ms.client.infra.errors.BadRequestException;
import com.ms.client.infra.errors.NotFoundException;
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
@Operation(summary = "Add a new company address", description = "Returns the company with new address", tags = {"Company"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "The address was added", content = @Content(
	schema = @Schema(implementation = Company.class)
  )),
  @ApiResponse(responseCode = "400", description = "The data is invalid", content = @Content(
	schema = @Schema(implementation = BadRequestException.class)
  )),
  @ApiResponse(responseCode = "403", description = "The manager does not have permission", content = @Content(
	schema = @Schema(implementation = UnauthorizedException.class)
  )),
  @ApiResponse(responseCode = "404", description = "The company not exists", content = @Content(
	schema = @Schema(implementation = NotFoundException.class)
  ))
})
public @interface ApiOperationAddAddress {}
