package com.ms.client.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@ComponentScan({"com.ms.client.infra", "com.ms.client.main"})
@EntityScan("com.ms.client.domain.entities")
@EnableJpaRepositories("com.ms.client.infra.repositories")
@EnableFeignClients("com.ms.client.infra.proxies")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

