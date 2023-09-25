package com.ms.electronic.invoice.infra.controllers.docs;

import com.ms.electronic.invoice.infra.errors.NotFoundException;
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
@Operation(summary = "Cancel invoice", description = "Allow cancel an invoice", tags = {"Invoice"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "The invoice was canceled", content = @Content),
  @ApiResponse(responseCode = "404", description = "The invoice was not found", content = @Content(
	schema = @Schema(implementation = NotFoundException.class)
  ))
})
public @interface ApiOperationCancel {}
