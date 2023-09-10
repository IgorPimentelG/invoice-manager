package com.ms.email.main;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.ms.email.infra", "com.ms.email.main"})
@EntityScan("com.ms.email.domain.entities")
@EnableJpaRepositories("com.ms.email.infra.repositories")
@OpenAPIDefinition(info = @Info(title = "MS Email", version = "0.0.1"))
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
