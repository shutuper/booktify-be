package org.qqsucc.booktify.user.repository;

import org.qqsucc.booktify.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByPhone(String email);

	boolean existsByPhone(String phone);

	boolean existsByEmail(String email);
}
