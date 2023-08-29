package com.ms.email.infra.controllers.docs.email;

import com.ms.email.domain.entities.Email;
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
@Operation(summary = "Find all email registers", description = "Returns all registers paginated", tags = {"Email"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "All records found", content = @Content(
	array = @ArraySchema(schema = @Schema(implementation = Email.class))
  ))
})
public @interface DocFindAllEmailRegisters {}
