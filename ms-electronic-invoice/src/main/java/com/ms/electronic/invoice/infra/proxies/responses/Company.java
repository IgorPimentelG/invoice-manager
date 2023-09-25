package com.ms.electronic.invoice.infra.proxies.responses;

import com.ms.electronic.invoice.domain.entities.Address;

import java.time.LocalDateTime;
import java.util.List;

public record Company(
  String id,
  String corporateName,
  String taxRegime,
  String cnpj,
  String email,
  String phone,
  LocalDateTime createdAt,
  LocalDateTime updatedAt,
  List<Address> address
) {}
