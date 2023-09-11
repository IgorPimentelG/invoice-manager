package com.ms.client.main;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.ms.client.infra", "com.ms.client.main"})
@EntityScan("com.ms.client.domain.entities")
@EnableJpaRepositories("com.ms.client.infra.repositories")
@EnableFeignClients("com.ms.client.infra.proxies")
@OpenAPIDefinition(info = @Info(title = "MS Client", version = "0.0.1"))
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

