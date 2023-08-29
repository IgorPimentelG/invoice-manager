package com.ms.email.infra.controllers.docs.email;

import com.ms.email.domain.entities.Email;
import com.ms.email.domain.errors.NotFoundException;
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
@Operation(summary = "Find email register", description = "Returns the email register", tags = {"Email"})
@ApiResponses({
  @ApiResponse(responseCode = "200", description = "Registered email details", content = @Content(
	schema = @Schema(implementation = Email.class)
  )),
  @ApiResponse(responseCode = "404", description = "Register email was not found", content = @Content(
	schema = @Schema(implementation = NotFoundException.class)
  ))
})
public @interface DocFindEmailRegister {}
