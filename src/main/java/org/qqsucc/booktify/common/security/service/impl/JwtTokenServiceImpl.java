package org.qqsucc.booktify.common.security.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.CollectionUtils;
import org.qqsucc.booktify.common.exception.InvalidTokenException;
import org.qqsucc.booktify.common.security.bean.TokenType;
import org.qqsucc.booktify.common.security.constants.JwtTokenConstants;
import org.qqsucc.booktify.common.security.service.JwtTokenService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class JwtTokenServiceImpl implements JwtTokenService {

	JwtTokenConstants jwtTokenConstants;

	public Jws<Claims> parseJwsClaims(String token, TokenType tokenType) {
		try {
			return Jwts.parser()
					.verifyWith(jwtTokenConstants.getTokenSecretKey(tokenType))
					.build()
					.parseSignedClaims(token);

		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidTokenException(tokenType.getTokenName() + " is invalid");
		}
	}

	public void validateJwsClaims(Jws<Claims> jwsClaims, TokenType tokenType) {
		if (jwsClaims.getPayload().getExpiration().before(new Date())) {
			throw new InvalidTokenException(tokenType.getTokenName() + " is expired");
		}
	}

	public Map<TokenType, String> generateTokens(String subject, Set<TokenType> tokenTypes) {
		if (CollectionUtils.isEmpty(tokenTypes)) {
			return Collections.emptyMap();
		}

		return tokenTypes
				.stream()
				.collect(Collectors.toMap(
						Function.identity(), tokenType -> generateToken(subject, tokenType)
				));
	}

	public String generateToken(String subject, TokenType tokenType) {
		Long tokenExpirationTime = jwtTokenConstants.getTokenExpirationTime(tokenType);
		SecretKey tokenSecretKey = jwtTokenConstants.getTokenSecretKey(tokenType);
		return Jwts
				.builder()
				.subject(subject)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
				.signWith(tokenSecretKey)
				.compact();
	}

}
