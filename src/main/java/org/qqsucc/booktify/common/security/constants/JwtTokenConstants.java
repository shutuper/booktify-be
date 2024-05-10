package org.qqsucc.booktify.common.security.constants;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.qqsucc.booktify.common.security.bean.TokenType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtTokenConstants {

	private final SecretKey ACCESS_TOKEN_SECRET_KEY;
	private final Long ACCESS_TOKEN_EXPIRATION;
	private final SecretKey REFRESH_SECRET_SECRET_KEY;
	private final Long REFRESH_SECRET_EXPIRATION;
	private final SecretKey PASSWORD_RESET_TOKEN_SECRET_KEY;
	private final Long PASSWORD_RESET_TOKEN_EXPIRATION;

	public JwtTokenConstants(@Value("${jwt.access-token.secret-key}") String accessTokenSecretKey,
							 @Value("${jwt.access-token.expiration}") Long accessTokenExpiration,
							 @Value("${jwt.refresh-token.secret-key}") String refreshSecretSecretKey,
							 @Value("${jwt.refresh-token.expiration}") Long refreshSecretExpiration,
							 @Value("${jwt.reset-token.secret-key}") String passwordResetTokenSecretKey,
							 @Value("${jwt.reset-token.expiration}") Long passwordResetTokenExpiration) {
		ACCESS_TOKEN_SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(
				accessTokenSecretKey
		));
		REFRESH_SECRET_SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(
				refreshSecretSecretKey
		));
		PASSWORD_RESET_TOKEN_SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(
				passwordResetTokenSecretKey
		));
		ACCESS_TOKEN_EXPIRATION = accessTokenExpiration;
		REFRESH_SECRET_EXPIRATION = refreshSecretExpiration;
		PASSWORD_RESET_TOKEN_EXPIRATION = passwordResetTokenExpiration;
	}

	public SecretKey getTokenSecretKey(TokenType tokenType) {
		return switch (tokenType) {
			case ACCESS -> ACCESS_TOKEN_SECRET_KEY;
			case REFRESH -> REFRESH_SECRET_SECRET_KEY;
			case RESET_PASSWORD -> PASSWORD_RESET_TOKEN_SECRET_KEY;
		};
	}

	public Long getTokenExpirationTime(TokenType tokenType) {
		return switch (tokenType) {
			case ACCESS -> ACCESS_TOKEN_EXPIRATION;
			case REFRESH -> REFRESH_SECRET_EXPIRATION;
			case RESET_PASSWORD -> PASSWORD_RESET_TOKEN_EXPIRATION;
		};
	}

}
