package org.qqsucc.booktify.procedure.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.exception.NotFoundException;
import org.qqsucc.booktify.procedure.repository.ProcedureRepository;
import org.qqsucc.booktify.procedure.repository.entity.Procedure;
import org.qqsucc.booktify.procedure.repository.entity.enumeration.ProcedureStatus;
import org.qqsucc.booktify.procedure.service.ProcedureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ProcedureServiceImpl implements ProcedureService {

	ProcedureRepository procedureRepository;

	@Override
	public Procedure save(Procedure procedure) {
		return procedureRepository.save(procedure);
	}

	@Override
	public Procedure findByIdAndMasterId(UUID procedureId, UUID masterId) {
		return procedureRepository
				.findByIdAndMasterId(procedureId, masterId)
				.orElseThrow(() -> new NotFoundException("Service not found"));
	}

	@Override
	public Page<Procedure> findAllByMasterIdAndStatus(UUID masterId, ProcedureStatus status, Pageable pageable) {
		return procedureRepository.findAllByMasterIdAndStatus(masterId, status, pageable);
	}

	@Override
	public Procedure findById(UUID procedureId) {
		return procedureRepository
				.findById(procedureId)
				.orElseThrow(() -> new NotFoundException("Service not found"));
	}

	@Override
	@Transactional
	public void deleteByIdAndMasterId(UUID procedureId, UUID masterId) {
		procedureRepository.deleteByIdAndMasterId(procedureId, masterId);
	}
}
