package com.ms.email.main.properties;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, int code, LocalDateTime timestamp) {}
