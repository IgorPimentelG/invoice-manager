package com.ms.tax.calculator.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan({"com.ms.tax.calculator.infra", "com.ms.tax.calculator.main"})
@EnableFeignClients("com.ms.tax.calculator.infra.proxies")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
