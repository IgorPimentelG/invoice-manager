package com.ms.service.auth.infra.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.service.auth.domain.entities.User;
import com.ms.service.auth.infra.error.InvalidTokenException;
import com.ms.service.auth.infra.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

	@Value("${security.jwt.token.secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.issuer}")
	private String issuer;

	@Autowired
	private UserRepository repository;

	public User validateToken(String token) {
		try {
			DecodedJWT claims = JWT.require(getAlgorithm())
			  .withIssuer(issuer)
			  .build()
			  .verify(token);

			if (claims.getExpiresAt().before(new Date())) {
				throw new InvalidTokenException();
			}

			return repository.findByEmail(claims.getSubject())
			  .orElseThrow(InvalidTokenException::new);
		} catch (Exception e) {
			throw new InvalidTokenException(e.getMessage());
		}
	}

	private Algorithm getAlgorithm() {
		return Algorithm.HMAC256(secretKey);
	}
}
