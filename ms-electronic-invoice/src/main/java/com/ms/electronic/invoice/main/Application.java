package com.ms.electronic.invoice.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@ComponentScan({"com.ms.electronic.invoice.infra", "com.ms.electronic.invoice.main"})
@EntityScan("com.ms.electronic.invoice.domain.entities")
@EnableJpaRepositories("com.ms.electronic.invoice.infra.repositories")
@EnableFeignClients("com.ms.electronic.invoice.infra.proxies")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
