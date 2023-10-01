package com.ms.electronic.invoice.infra.proxies;

import com.ms.electronic.invoice.infra.proxies.responses.Company;
import com.ms.electronic.invoice.main.config.FeignClientJwtAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
  name = "ms-client",
  url = "http://localhost:8080/ms-client/api/company",
  configuration = FeignClientJwtAuthConfig.class
)
public interface CompanyProxy {
	@GetMapping("/v1/find/{cnpj}")
	Company getCompany(@PathVariable("cnpj") String cnpj);
}
