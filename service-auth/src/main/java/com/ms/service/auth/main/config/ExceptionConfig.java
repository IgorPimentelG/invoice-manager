package com.ms.service.auth.main.config;

import com.ms.service.auth.infra.errors.InvalidTokenException;
import com.ms.service.auth.main.properties.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleDefaultException(Exception ex) {
		return new ResponseEntity<>(
		  responseFactory(ex, 400),
		  HttpStatus.BAD_REQUEST
		);
	}

	@ExceptionHandler(InvalidTokenException.class)
	public final ResponseEntity<ExceptionResponse> handleForbiddenException(Exception ex) {
		return new ResponseEntity<>(
		  responseFactory(ex, 403),
		  HttpStatus.FORBIDDEN
		);
	}

	private ExceptionResponse responseFactory(Exception ex, int code) {
		return new ExceptionResponse(
		  ex.getMessage(),
		  code,
		  LocalDateTime.now()
		);
	}
}
