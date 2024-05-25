package org.qqsucc.booktify.common.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.user.repository.entity.enums.UserStatus;
import org.qqsucc.booktify.user.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class BooktifyScheduler {

	UserService userService;

	// every 30 minutes, initial delay - 10 min
	@Scheduled(fixedDelay = 1_800_000, initialDelay = 10 * 60 * 1000)
	public void deleteExpiredNotActiveUsers() {
		userService.deleteByStatusAndCreatedDateLessThan(
				UserStatus.NOT_ACTIVE, Instant.now().minus(30, ChronoUnit.MINUTES)
		);
	}

}
