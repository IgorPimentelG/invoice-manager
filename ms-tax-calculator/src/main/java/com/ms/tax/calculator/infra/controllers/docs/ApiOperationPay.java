package com.ms.tax.calculator.infra.controllers.docs;

import com.ms.tax.calculator.domain.entities.TaxResume;
import com.ms.tax.calculator.infra.errors.UnauthorizedException;
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
@Operation(summary = "Update payment", description = "Returns tax info paid", tags = {"Calculator"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "Tax paid", content = @Content(
	schema = @Schema(implementation = TaxResume.class)
  )),
  @ApiResponse(responseCode = "404", description = "Tax not found", content = @Content(
	schema = @Schema(implementation = UnauthorizedException.class)
  )),
  @ApiResponse(responseCode = "409", description = "User does not have authorization", content = @Content(
	schema = @Schema(implementation = UnauthorizedException.class)
  ))
})
public @interface ApiOperationPay {}
