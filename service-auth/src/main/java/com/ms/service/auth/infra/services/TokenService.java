package com.ms.service.auth.infra.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.service.auth.infra.dtos.TokenDto;
import com.ms.service.auth.infra.error.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

	@Value("${security.jwt.token.secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.issuer}")
	private String issuer;

	public TokenDto validateToken(String token) {
		try {
			var isValid = false;

			DecodedJWT claims = JWT.require(getAlgorithm())
			  .withIssuer(issuer)
			  .build()
			  .verify(token);

			if (claims.getExpiresAt().after(new Date())) {
				isValid = true;
			}

			return new TokenDto(token, claims.getSubject(), isValid);
		} catch (Exception e) {
			throw new InvalidTokenException(e.getMessage());
		}
	}

	private Algorithm getAlgorithm() {
		return Algorithm.HMAC256(secretKey);
	}
}
