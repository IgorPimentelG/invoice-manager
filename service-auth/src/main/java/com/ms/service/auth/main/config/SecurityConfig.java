package com.ms.service.auth.main.config;

import com.ms.service.auth.main.properties.DefaultUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
		  .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
		  .httpBasic(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsManager(DefaultUser defaultUser, PasswordEncoder encoder) {
		return new InMemoryUserDetailsManager(
		  User.builder()
		    .username(defaultUser.getUsername())
		    .password(encoder.encode(defaultUser.getPassword()))
		    .build()
		);
	}
}
