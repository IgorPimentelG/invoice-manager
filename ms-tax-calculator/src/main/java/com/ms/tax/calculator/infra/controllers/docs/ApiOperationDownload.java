package com.ms.tax.calculator.infra.controllers.docs;

import com.ms.tax.calculator.infra.errors.BadRequestException;
import com.ms.tax.calculator.infra.errors.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.Resource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Download taxes", description = "Returns tax file", tags = {"Calculator"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "Tax file was found", content = @Content(
	schema = @Schema(implementation = Resource.class)
  )),
  @ApiResponse(responseCode = "400", description = "Unable to load file", content = @Content(
	schema = @Schema(implementation = BadRequestException.class)
  )),
  @ApiResponse(responseCode = "404", description = "Tax file not found.", content = @Content(
	schema = @Schema(implementation = NotFoundException.class)
  ))
})
public @interface ApiOperationDownload {}
