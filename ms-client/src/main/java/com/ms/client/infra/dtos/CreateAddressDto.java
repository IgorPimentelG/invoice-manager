package com.ms.client.infra.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record CreateAddressDto(
  @NotEmpty(message = "State is required")
  @Pattern(
	regexp = "^[A-Z][A-Z]$",
	message = "The state must be only the abbreviation in capital letters."
  )
  String state,

  @NotEmpty(message = "City is required")
  String city,

  @NotEmpty(message = "Street is required")
  String street,

  @NotEmpty(message = "Neighborhood is required")
  String neighborhood,

  @NotEmpty(message = "Number is required")
  @Pattern(
	regexp = "^\\d+(-[A-Z]+)?$",
	message = "Number is not valid. Insert in the following format: 00 or 000-A."
  )
  String number,

  @NotEmpty(message = "Zip code is required")
  @Pattern(
	regexp = "^\\d{5}-\\d{3}$",
	message = "Zip code is not valid. Insert in the following format: xxxxx-xx."
  )
  String zipCode
) {}
