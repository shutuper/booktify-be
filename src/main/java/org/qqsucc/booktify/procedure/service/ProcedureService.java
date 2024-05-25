package org.qqsucc.booktify.procedure.service;

import org.qqsucc.booktify.procedure.repository.entity.Procedure;
import org.qqsucc.booktify.procedure.repository.entity.enumeration.ProcedureStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProcedureService {

	Procedure save(Procedure procedure);

	Procedure findByIdAndMasterId(UUID procedureId, UUID masterId);

	Page<Procedure> findAllByMasterIdAndStatus(UUID masterId, ProcedureStatus status, Pageable pageable);

	Procedure findById(UUID procedureId);

	void deleteByIdAndMasterId(UUID procedureId, UUID masterId);

}
