package com.ms.client.infra.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.dtos.CredentialsDto;
import com.ms.client.infra.errors.*;
import com.ms.client.infra.repositories.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

	@Value("${security.jwt.token.secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length}")
	private long validityInMilliseconds;

	@Value("${security.jwt.token.issuer-url}")
	private String issuerUrl;

	@Autowired
	private ManagerRepository repository;

	public CredentialsDto createToken(Manager manager) {

		Date now = new Date();
		var expirationAccessToken = getExpirationAccessToken(now);
		var expirationRefreshToken = getExpirationRefreshToken(now);

		var accessToken = getAccessToken(manager, expirationAccessToken);
		var refreshToken = getRefreshToken(manager, now, expirationRefreshToken);

		return new CredentialsDto(
		  accessToken,
		  refreshToken,
		  expirationAccessToken,
		  expirationRefreshToken
		);
	}

	public CredentialsDto refreshToken(String refreshToken) {

		if (refreshToken.contains("Bearer ")) {
			refreshToken = refreshToken.substring("Bearer ".length());
		} else {
			throw new UnauthorizedException();
		}

		JWTVerifier verifier = JWT.require(getAlgorithm()).build();
		DecodedJWT decodedJWT = verifier.verify(refreshToken);
		String subject = decodedJWT.getSubject();

		var manager = repository.findByEmail(subject)
		  .orElseThrow(() -> new NotFoundException("Manager"));

		return createToken(manager);
	}

	public String validateToken(String token) {
		return JWT.require(getAlgorithm())
		  .withIssuer(issuerUrl)
		  .build()
		  .verify(token)
		  .getSubject();
	}

	public Date getExpirationAccessToken(Date now) {
		return new Date(now.getTime() + validityInMilliseconds);
	}

	public Date getExpirationRefreshToken(Date now) {
		return new Date(now.getTime() + (validityInMilliseconds * 3));
	}

	private String getAccessToken(Manager manager, Date expiration) {
		return JWT.create()
		  .withIssuer(issuerUrl)
		  .withSubject(manager.getEmail())
		  .withExpiresAt(expiration)
		  .sign(getAlgorithm());
	}

	private String getRefreshToken(Manager manager, Date now, Date expiration) {
		return JWT.create()
		  .withIssuedAt(now)
		  .withExpiresAt(expiration)
		  .withSubject(manager.getEmail())
		  .sign(getAlgorithm())
		  .strip();
	}

	private Algorithm getAlgorithm() {
		return Algorithm.HMAC256(secretKey);
	}
}
