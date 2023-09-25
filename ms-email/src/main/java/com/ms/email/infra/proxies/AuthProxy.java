package com.ms.email.infra.proxies;

import com.ms.email.infra.proxies.responses.User;
import com.ms.email.main.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
  name = "service-auth",
  url = "http://localhost:8080/service-auth/api/auth",
  configuration = FeignClientConfig.class
)
public interface AuthProxy {
	@GetMapping("/token/validate")
	User validateToken(@RequestParam("accessToken") String accessToken);
}
