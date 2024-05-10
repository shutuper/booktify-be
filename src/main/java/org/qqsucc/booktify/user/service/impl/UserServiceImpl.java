package org.qqsucc.booktify.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.exception.NotFoundException;
import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.repository.UserRepository;
import org.qqsucc.booktify.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class UserServiceImpl implements UserService {

	UserRepository userRepository;

	@Override
	public Optional<User> findOptByPhone(String phone) {
		return userRepository.findByPhone(phone);
	}

	@Override
	public User findByPhone(String phone) {
		return findOptByPhone(phone).orElseThrow(() -> new NotFoundException("User not found"));
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
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
}
