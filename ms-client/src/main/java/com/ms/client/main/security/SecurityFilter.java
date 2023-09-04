package com.ms.client.main.security;

import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.errors.NotFoundException;
import com.ms.client.infra.repositories.ManagerRepository;
import com.ms.client.infra.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService service;

	@Autowired
	private ManagerRepository repository;

	@Override
	protected void doFilterInternal(
	  HttpServletRequest request,
	  HttpServletResponse response,
	  FilterChain chain
	) throws ServletException, IOException {

		var token = getToken(request);

		if (token != null) {
			var subject = service.validateToken(token);
			Manager manager = repository.findByEmail(subject)
			  .orElseThrow(() -> new NotFoundException("Manager"));

			var auth = new UsernamePasswordAuthenticationToken(manager, null, manager.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		chain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		return authHeader == null ? null : authHeader.replace("Bearer ", "");
	}
}
