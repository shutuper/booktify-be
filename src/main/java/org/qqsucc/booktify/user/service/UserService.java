package org.qqsucc.booktify.user.service;

import org.qqsucc.booktify.user.repository.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

	Optional<User> findOptByPhone(String phone);

	User findByPhone(String phone);

	List<User> findAll();

	boolean existsByPhone(String phone);

	boolean existsByEmail(String email);

	User save(User user);

	User findById(UUID userId);
}
