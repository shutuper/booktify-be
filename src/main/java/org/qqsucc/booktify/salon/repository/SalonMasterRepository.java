package org.qqsucc.booktify.salon.repository;

import org.qqsucc.booktify.salon.repository.entity.SalonMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalonMasterRepository extends JpaRepository<SalonMaster, UUID> {

	boolean existsByMasterId(UUID masterId);
}
