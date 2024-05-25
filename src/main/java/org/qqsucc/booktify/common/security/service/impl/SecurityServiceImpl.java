package org.qqsucc.booktify.common.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.exception.NotFoundException;
import org.qqsucc.booktify.common.security.bean.CustomUserDetails;
import org.qqsucc.booktify.common.security.service.SecurityService;
import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SecurityServiceImpl implements SecurityService {

	UserService userService;

	@Override
	public CustomUserDetails getAuthUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			throw new NotFoundException("Authenticated user not found");
		}

		return (CustomUserDetails) authentication.getPrincipal();
	}

	@Override
	public User getAuthUser() {
		return userService.findById(getAuthUserId());
	}

	@Override
	public UUID getAuthUserId() {
		return getAuthUserDetails().getId();
	}

	@Override
	public Optional<UUID> getAuthUserIdOpt() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}

		return Optional.of(((CustomUserDetails) authentication.getPrincipal()).getId());
	}
}
