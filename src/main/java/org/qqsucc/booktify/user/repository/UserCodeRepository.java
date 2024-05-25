package org.qqsucc.booktify.user.repository;

import org.qqsucc.booktify.user.repository.entity.UserCode;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserCodeRepository extends JpaRepository<UserCode, UUID> {

	@EntityGraph(attributePaths = "user")
	Optional<UserCode> findByUserIdAndCode(UUID userId, String code);
}
