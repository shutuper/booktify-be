package org.qqsucc.booktify.user.repository;

import org.qqsucc.booktify.user.repository.entity.User;
import org.qqsucc.booktify.user.repository.entity.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findByPhone(String email);

	boolean existsByPhone(String phone);

	boolean existsByEmail(String email);

	Page<User> findAllByStatusAndSalonMaster_SalonId(UserStatus userStatus, UUID salonId, Pageable pageable);

	void deleteByStatusAndCreatedDateLessThan(UserStatus userStatus, Instant date);

	Optional<User> findByStatusAndIdAndSalonMaster_SalonId(UserStatus userStatus, UUID masterId, UUID salonId);
}
