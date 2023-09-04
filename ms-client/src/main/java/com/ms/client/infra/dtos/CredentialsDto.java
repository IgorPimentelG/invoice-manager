package com.ms.client.infra.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record CredentialsDto(
  @JsonProperty("access_token")
  String accessToken,

  @JsonProperty("refresh_token")
  String refreshToken,

  @JsonProperty("expiration_access_token")
  Date expirationAccessToken,

  @JsonProperty("expiration_refresh_token")
  Date expirationRefreshToken
) {}
