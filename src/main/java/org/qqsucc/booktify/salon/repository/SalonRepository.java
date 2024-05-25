package org.qqsucc.booktify.salon.repository;

import org.qqsucc.booktify.salon.repository.entity.Salon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SalonRepository extends JpaRepository<Salon, UUID> {

	boolean existsByLinkName(String linkName);

	Optional<Salon> findByIdAndSalonMasters_MasterId(UUID salonId, UUID masterId);

	Optional<Salon> findBySalonMasters_MasterId(UUID masterId);

	Optional<Salon> findByLinkName(String linkName);
}
