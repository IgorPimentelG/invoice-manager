package com.ms.service.auth.infra.dtos;

public record TokenDto(String token, String subject, boolean isValid) {}
