package com.ms.electronic.invoice.infra.proxies;


import com.ms.electronic.invoice.infra.proxies.responses.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ms-client", url = "http://localhost:8080/ms-client/api/company")
public interface CompanyProxy {
	@GetMapping("/v1/find/{cnpj}")
	Company getCompany(
	  @PathVariable("cnpj") String cnpj,
	  @RequestHeader("Authorization") String authorization
	);
}
