package org.qqsucc.booktify.user.service;

import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.repository.entity.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

	Optional<User> findOptByPhone(String phone);

	User findByPhone(String phone);

	boolean existsByPhone(String phone);

	boolean existsByEmail(String email);

	User save(User user);

	User findById(UUID userId);

	Page<User> findAllMastersBySalonId(UUID salonId, Pageable pageable);

	void deleteByStatusAndCreatedDateLessThan(UserStatus userStatus, Instant date);

	User findBySalonIdAndMasterId(UUID salonId, UUID masterId);

}
