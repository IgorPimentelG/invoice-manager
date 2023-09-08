package com.ms.client.infra.dtos;

import jakarta.validation.constraints.Pattern;

public class UpdateAddressDto {
  @Pattern(
    regexp = "^[A-Z][A-Z]$",
    message = "The state must be only the abbreviation in capital letters."
  )
  String state;

  String city;
  String street;
  String neighborhood;

  @Pattern(
    regexp = "^\\d+(-[A-Z]+)?$",
    message = "Number is not valid. Insert in the following format: 00 or 000-A."
  )
  String number;

  @Pattern(
    regexp = "^\\d{5}-\\d{3}$",
    message = "Zip code is not valid. Insert in the following format: xxxxx-xx."
  )
  String zipCode;
}
