package org.qqsucc.booktify.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.exception.BusinessException;
import org.qqsucc.booktify.common.exception.handler.response.ErrorMessage;
import org.qqsucc.booktify.common.security.bean.CustomUserDetails;
import org.qqsucc.booktify.common.security.bean.TokenType;
import org.qqsucc.booktify.common.security.service.JwtTokenService;
import org.qqsucc.booktify.common.security.service.impl.CustomUserDetailsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static lombok.AccessLevel.PRIVATE;
import static org.qqsucc.booktify.common.security.constants.SecurityConstants.*;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class JwtAuthFilter extends OncePerRequestFilter {

	JwtTokenService jwtTokenService;
	ObjectMapper objectMapper;
	CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		try {
			String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			String authToken = authHeader.substring(TOKEN_PREFIX.length());

			Jws<Claims> jwsClaims = jwtTokenService.parseJwsClaims(authToken, TokenType.ACCESS);
			jwtTokenService.validateJwsClaims(jwsClaims, TokenType.ACCESS);

			CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(
					jwsClaims.getPayload().getSubject()
			);

			SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities()
			));
		} catch (BusinessException ex) {
			SecurityContextHolder.clearContext();
			sendUnAuthorizedErrorMessage(response, ex);
			return;
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		if (request.getRequestURI().startsWith(PUBLIC_APIS_FILTER)) {
			return true;
		}

		String authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
		return authHeader == null || !authHeader.startsWith(TOKEN_PREFIX);
	}

	private void sendUnAuthorizedErrorMessage(HttpServletResponse response, Exception exception) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		objectMapper.writeValue(response.getWriter(), new ErrorMessage(
				System.currentTimeMillis(),
				HttpStatus.UNAUTHORIZED.value(),
				exception.getMessage()
		));
	}

}
