package org.qqsucc.booktify.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.qqsucc.booktify.common.exception.BusinessException;
import org.qqsucc.booktify.user.repository.UserCodeRepository;
import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.repository.entity.UserCode;
import org.qqsucc.booktify.user.service.UserCodeService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserCodeServiceImpl implements UserCodeService {

	UserCodeRepository userCodeRepository;

	@Override
	public UserCode generateCode(UUID userId) {
		return userCodeRepository.save(
				UserCode.builder()
						.expirationDate(Instant.now().plus(5, ChronoUnit.MINUTES))
						.code(RandomStringUtils.randomNumeric(6))
						.userId(userId)
						.build()
		);
	}

	@Override
	public User verifyCode(UUID userId, String code) {
		UserCode userCode = userCodeRepository
				.findByUserIdAndCode(userId, code)
				.orElseThrow(() -> new BusinessException("Invalid user code"));

		if (Instant.now().isAfter(userCode.getExpirationDate())) {
			throw new BusinessException("Code is already expired");
		}

		userCodeRepository.deleteById(userCode.getId());

		return userCode.getUser();
	}
}
