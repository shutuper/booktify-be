package org.qqsucc.booktify.procedure.repository;

import org.qqsucc.booktify.procedure.repository.entity.Procedure;
import org.qqsucc.booktify.procedure.repository.entity.enumeration.ProcedureStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProcedureRepository extends JpaRepository<Procedure, UUID> {

	Optional<Procedure> findByIdAndMasterId(UUID procedureId, UUID masterId);

	Page<Procedure> findAllByMasterIdAndStatus(UUID masterId, ProcedureStatus status, Pageable pageable);

	void deleteByIdAndMasterId(UUID procedureId, UUID masterId);
}
