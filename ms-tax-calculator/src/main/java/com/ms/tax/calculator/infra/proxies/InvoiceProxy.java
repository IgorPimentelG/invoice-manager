package com.ms.tax.calculator.infra.proxies;

import com.ms.tax.calculator.infra.proxies.responses.Invoice;
import com.ms.tax.calculator.main.config.FeignClientJwtAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(
  name = "ms-electronic-invoice",
  url = "http://localhost:8080/ms-electronic-invoice/api/invoice",
  configuration = FeignClientJwtAuthConfig.class
)
public interface InvoiceProxy {
	@GetMapping("/v1/list/{cnpj}")
	List<Invoice> listAll(@PathVariable("cnpj") String cnpj);
}
