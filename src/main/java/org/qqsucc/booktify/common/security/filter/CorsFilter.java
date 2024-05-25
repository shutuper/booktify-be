package org.qqsucc.booktify.common.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static lombok.AccessLevel.PRIVATE;

@Setter
@Component
@FieldDefaults(level = PRIVATE)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter extends OncePerRequestFilter {

	@NonFinal
	@Value("#{'${cors.origin}'.split(',')}")
	Set<String> ALLOWED_ORIGINS;

	@Override
	protected void doFilterInternal(HttpServletRequest httpRequest, @NotNull HttpServletResponse httpResponse,
									@NotNull FilterChain filterChain) throws ServletException, IOException {
		String origin = httpRequest.getHeader(HttpHeaders.ORIGIN);

		if (!ALLOWED_ORIGINS.contains("*") && !ALLOWED_ORIGINS.contains(origin)) {
			httpResponse.setStatus(SC_FORBIDDEN);
			return;
		}

		httpResponse.setHeader("Access-Control-Allow-Origin", origin);
		httpResponse.setHeader("Access-Control-Expose-Headers", "*");
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, OPTIONS, DELETE");
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers", """
				Content-Type, User-Agent, X-Requested-With, Cache-Control, Accept, Origin,
				X-Auth-Token, X-User-Id, schemaName, Authorization, Response-Type"""
		);

		if (HttpMethod.OPTIONS.name().equalsIgnoreCase(httpRequest.getMethod())) {
			httpResponse.setStatus(HttpServletResponse.SC_OK);
		} else {
			filterChain.doFilter(httpRequest, httpResponse);
		}
	}

}
