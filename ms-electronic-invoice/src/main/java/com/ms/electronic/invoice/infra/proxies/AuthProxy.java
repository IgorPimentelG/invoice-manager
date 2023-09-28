package com.ms.electronic.invoice.infra.proxies;

import com.ms.electronic.invoice.infra.proxies.responses.User;
import com.ms.electronic.invoice.main.config.FeignClientBasicAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
  name = "service-auth",
  url = "http://localhost:8080/service-auth/api/auth",
  configuration = FeignClientBasicAuthConfig.class
)
public interface AuthProxy {
	@GetMapping("/token/validate")
	User validateToken(@RequestParam("accessToken") String accessToken);
}
