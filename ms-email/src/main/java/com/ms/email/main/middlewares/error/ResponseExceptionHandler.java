package com.ms.email.main.middlewares.error;

import com.ms.email.infra.errors.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleDefaultException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
		  responseFactory(ex, 400),
		  HttpStatus.BAD_REQUEST
		);
	}

	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request) {
		return new ResponseEntity<>(
		  responseFactory(ex, 404),
		  HttpStatus.NOT_FOUND
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
