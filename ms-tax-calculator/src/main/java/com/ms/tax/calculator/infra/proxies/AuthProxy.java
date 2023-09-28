package com.ms.tax.calculator.infra.proxies;

import com.ms.tax.calculator.infra.proxies.responses.User;
import com.ms.tax.calculator.main.config.FeignClientBaseAuthConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
  name = "service-auth",
  url = "http://localhost:8080/service-auth/api/auth",
  configuration = FeignClientBaseAuthConfig.class
)
public interface AuthProxy {
	@GetMapping("/token/validate")
	User validateToken(@RequestParam("accessToken") String accessToken);
}
