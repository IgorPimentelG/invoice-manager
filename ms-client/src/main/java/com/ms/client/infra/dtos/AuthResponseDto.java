package com.ms.client.infra.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ms.client.domain.entities.Manager;

public record AuthResponseDto(
  @JsonProperty("user_details")
  Manager manager,

  @JsonProperty("credentials_details")
  CredentialsDto credentials
) {}
