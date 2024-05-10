package org.qqsucc.booktify.user.repository.entity.enums;

import lombok.Getter;

@Getter
public enum UserRole {

	ROLE_CLIENT("CLIENT"),
	ROLE_MASTER("MASTER"),
	ROLE_ADMIN("ADMIN");

	private final String role;

	UserRole(String role) {
		this.role = role;
	}
}
