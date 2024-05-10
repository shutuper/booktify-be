package org.qqsucc.booktify.common.security.bean;

import lombok.Getter;

@Getter
public enum TokenType {

	ACCESS("Access token"),
	REFRESH("Refresh token"),
	RESET_PASSWORD("Reset password token");

	private final String tokenName;

	TokenType(String tokenName) {
		this.tokenName = tokenName;
	}
}
