package org.qqsucc.booktify.common.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.security.bean.CustomUserDetails;
import org.qqsucc.booktify.common.security.service.SecurityService;
import org.qqsucc.booktify.user.repository.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SecurityUtils {

	SecurityService securityService;

	private static SecurityService staticSecurityService;

	@PostConstruct
	public void init() {
		staticSecurityService = this.securityService;
	}

	public static UUID getAuthUserId() {
		return staticSecurityService.getAuthUserId();
	}

	public static Optional<UUID> getAuthUserIdOpt() {
		return staticSecurityService.getAuthUserIdOpt();
	}

	public static CustomUserDetails getAuthUserDetails() {
		return staticSecurityService.getAuthUserDetails();
	}

	public static User getAuthUser() {
		return staticSecurityService.getAuthUser();
	}

}
