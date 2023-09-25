package com.ms.electronic.invoice.infra.controllers.docs;

import com.ms.electronic.invoice.domain.entities.Invoice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@Operation(summary = "List all invoices", description = "Allow permit show all invoices by CNPJ", tags = {"Invoice"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "", content = @Content(
	array = @ArraySchema(schema = @Schema(implementation = Invoice.class))
  ))
})
public @interface ApiOperationList {}
