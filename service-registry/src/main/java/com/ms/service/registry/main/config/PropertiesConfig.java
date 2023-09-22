package com.ms.service.registry.main.config;

import com.ms.service.registry.main.properties.DefaultUser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {

	@Bean
	@ConfigurationProperties("security.credentials")
	public DefaultUser defaultUser() {
		return new DefaultUser();
	}
}
