package com.ms.client.infra.proxies;

import com.ms.client.infra.proxies.requests.EmailRequest;
import com.ms.client.infra.proxies.response.EmailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "ms-email", url = "http://localhost:8080/ms-email/api/email")
public interface EmailProxy {

	@PostMapping("/v1/send")
	EmailResponse sendEmail(EmailRequest email);
}
