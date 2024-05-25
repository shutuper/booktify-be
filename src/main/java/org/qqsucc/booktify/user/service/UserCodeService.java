package org.qqsucc.booktify.user.service;

import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.repository.entity.UserCode;

import java.util.UUID;

public interface UserCodeService {

	UserCode generateCode(UUID userId);

	User verifyCode(UUID userId, String code);

}
