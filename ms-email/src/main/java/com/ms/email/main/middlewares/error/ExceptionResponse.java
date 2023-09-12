package com.ms.email.main.middlewares.error;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, int code, LocalDateTime timestamp) {}
