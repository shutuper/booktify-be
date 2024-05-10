package org.qqsucc.booktify.common.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.qqsucc.booktify.common.security.bean.TokenType;

import java.util.Map;
import java.util.Set;

public interface JwtTokenService {

	Jws<Claims> parseJwsClaims(String token, TokenType tokenType);

	void validateJwsClaims(Jws<Claims> jwsClaims, TokenType tokenType);

	Map<TokenType, String> generateTokens(String subject, Set<TokenType> tokenTypes);

	String generateToken(String subject, TokenType tokenType);

}
