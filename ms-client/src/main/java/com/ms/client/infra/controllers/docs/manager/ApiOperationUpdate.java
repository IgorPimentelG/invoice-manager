package com.ms.client.infra.controllers.docs.manager;

import com.ms.client.domain.entities.Manager;
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
@Operation(summary = "Update manager", description = "Returns the manager updated", tags = {"Manager"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "The manager was updated", content = @Content(
	schema = @Schema(implementation = Manager.class)
  )),
  @ApiResponse(responseCode = "400", description = "The data is invalid", content = @Content(
	schema = @Schema(implementation = BadRequestException.class)
  )),
  @ApiResponse(responseCode = "403", description = "The manager does not have permission", content = @Content(
	schema = @Schema(implementation = UnauthorizedException.class)
  )),
  @ApiResponse(responseCode = "404", description = "The manager not exists", content = @Content(
	schema = @Schema(implementation = NotFoundException.class)
  ))
})
public @interface ApiOperationUpdate {}
