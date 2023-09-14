package com.ms.client.main.middlewares.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {

	@Autowired
	private SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.
		  csrf(AbstractHttpConfigurer::disable)
		  .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		  .authorizeHttpRequests(authorize -> authorize
		    .requestMatchers(
			  "/api/auth/sign-up",
		      "/api/auth/sign-in",
		      "/api/auth/reset",
		      "/api/auth/change-password/**",
		      "/api/auth/verify-code/**",
		      "/api/auth/recover-account/**",
		      "/v3/api-docs/**",
		      "/swagger-ui/**",
		      "/actuator/**"
		    ).permitAll()
		    .anyRequest().authenticated()
		  )
		  .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
		  .build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
	  throws Exception {
		return configuration.getAuthenticationManager();
	}
}
