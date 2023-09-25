package com.ms.electronic.invoice.infra.controllers.docs;

import com.ms.electronic.invoice.domain.entities.Invoice;
import com.ms.electronic.invoice.infra.errors.BadRequestException;
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
@Operation(summary = "Register invoice", description = "Allow register a new invoice", tags = {"Invoice"})
@ApiResponses({
  @ApiResponse(responseCode = "201", description = "The invoice was successfully registered", content = @Content(
	schema = @Schema(implementation = Invoice.class)
  )),
  @ApiResponse(responseCode = "400", description = "The invoice data is null", content = @Content(
	schema = @Schema(implementation = BadRequestException.class)
  ))
})
public @interface ApiOperationRegister {}
