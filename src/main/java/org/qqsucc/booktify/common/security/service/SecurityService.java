package org.qqsucc.booktify.common.security.service;

import org.qqsucc.booktify.common.security.bean.CustomUserDetails;
import org.qqsucc.booktify.user.repository.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface SecurityService {

	CustomUserDetails getAuthUserDetails();

	User getAuthUser();

	UUID getAuthUserId();

	Optional<UUID> getAuthUserIdOpt();
}
