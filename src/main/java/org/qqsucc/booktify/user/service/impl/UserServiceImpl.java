package org.qqsucc.booktify.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.exception.NotFoundException;
import org.qqsucc.booktify.user.repository.UserRepository;
import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.repository.entity.enums.UserStatus;
import org.qqsucc.booktify.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserServiceImpl implements UserService {

	UserRepository userRepository;

	private static final Sort DEFAULT_MASTER_SORT = Sort.by(Sort.Direction.DESC, "firstname", "lastname");

	@Override
	public Optional<User> findOptByPhone(String phone) {
		return userRepository.findByPhone(phone);
	}

	@Override
	public User findByPhone(String phone) {
		return findOptByPhone(phone).orElseThrow(() -> new NotFoundException("User not found"));
	}

	@Override
	public boolean existsByPhone(String phone) {
		return userRepository.existsByPhone(phone);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public User findById(UUID userId) {
		return userRepository
				.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found by id"));
	}

	@Override
	public Page<User> findAllMastersBySalonId(UUID salonId, Pageable pageable) {
		return userRepository.findAllByStatusAndSalonMaster_SalonId(
				UserStatus.ACTIVE, salonId, pageable
		);
	}

	@Override
	@Transactional
	public void deleteByStatusAndCreatedDateLessThan(UserStatus userStatus, Instant date) {
		userRepository.deleteByStatusAndCreatedDateLessThan(userStatus, date);
	}

	@Override
	public User findBySalonIdAndMasterId(UUID salonId, UUID masterId) {
		return userRepository
				.findByStatusAndIdAndSalonMaster_SalonId(UserStatus.ACTIVE, masterId, salonId)
				.orElseThrow(() -> new NotFoundException("Master not found"));
	}
}
