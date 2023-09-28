package com.ms.electronic.invoice.main.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Configuration
public class FeignClientJwtAuthConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate requestTemplate) {
				var request = ((ServletRequestAttributes) Objects.requireNonNull(
				  RequestContextHolder.getRequestAttributes())).getRequest();
				var accessToken = request.getHeader("Authorization");
				requestTemplate.header("Authorization", accessToken);
			}
		};
	}
}
