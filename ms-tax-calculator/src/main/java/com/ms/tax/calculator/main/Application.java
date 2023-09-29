package com.ms.tax.calculator.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@ComponentScan({"com.ms.tax.calculator.infra", "com.ms.tax.calculator.main"})
@EntityScan("com.ms.tax.calculator.domain.entities")
@EnableJpaRepositories("com.ms.tax.calculator.infra.repositories")
@EnableFeignClients("com.ms.tax.calculator.infra.proxies")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
