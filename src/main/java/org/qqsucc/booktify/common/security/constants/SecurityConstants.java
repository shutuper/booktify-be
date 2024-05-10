package org.qqsucc.booktify.common.security.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {

	public static final String PUBLIC_APIS = "/api/v1/public/**";
	public static final String PUBLIC_APIS_FILTER = "/api/v1/public";
	public static final String PRIVATE_APIS = "/api/v1/private/**";
	public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";


}
