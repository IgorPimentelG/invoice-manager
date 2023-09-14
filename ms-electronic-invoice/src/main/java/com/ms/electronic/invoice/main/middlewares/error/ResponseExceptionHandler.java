package com.ms.electronic.invoice.main.middlewares.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleDefaultException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
		  responseFactory(ex, 400),
		  HttpStatus.BAD_REQUEST
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
