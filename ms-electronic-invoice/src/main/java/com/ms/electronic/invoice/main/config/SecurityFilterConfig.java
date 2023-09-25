package com.ms.electronic.invoice.main.config;

import com.ms.electronic.invoice.infra.errors.UnauthorizedException;
import com.ms.electronic.invoice.infra.proxies.AuthProxy;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class SecurityFilterConfig extends OncePerRequestFilter {

	@Autowired
	private AuthProxy authProxy;

	@Override
	protected void doFilterInternal(
	  HttpServletRequest request,
	  HttpServletResponse response,
	  FilterChain chain
	) throws ServletException, IOException {

		var token = getToken(request);

		if (token != null) {
			try {
				var user = authProxy.validateToken(token);
				var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (Exception ignored) {
				throw new UnauthorizedException();
			}
		}

		chain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		return authHeader == null ? null : authHeader.replace("Bearer", " ");
	}
}
