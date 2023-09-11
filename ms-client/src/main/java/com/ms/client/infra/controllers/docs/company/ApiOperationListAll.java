package com.ms.client.infra.controllers.docs.company;

import com.ms.client.domain.entities.Company;
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
@Operation(summary = "List all companies", description = "Returns all companies created", tags = {"Company"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "All companies", content = @Content(
	array = @ArraySchema(schema = @Schema(implementation = Company.class))
  ))
})
public @interface ApiOperationListAll {}
