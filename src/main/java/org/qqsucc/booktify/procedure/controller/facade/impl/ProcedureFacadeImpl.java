package org.qqsucc.booktify.procedure.controller.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.util.SecurityUtils;
import org.qqsucc.booktify.procedure.controller.dto.AvailableDatesSearchDto;
import org.qqsucc.booktify.procedure.controller.dto.ProcedureDto;
import org.qqsucc.booktify.procedure.controller.dto.TimeslotResponseDto;
import org.qqsucc.booktify.procedure.controller.facade.ProcedureFacade;
import org.qqsucc.booktify.procedure.controller.mapper.ProcedureMapper;
import org.qqsucc.booktify.procedure.repository.entity.Procedure;
import org.qqsucc.booktify.procedure.repository.entity.enumeration.ProcedureStatus;
import org.qqsucc.booktify.procedure.service.ProcedureService;
import org.qqsucc.booktify.procedure.service.TimeslotService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ProcedureFacadeImpl implements ProcedureFacade {

	ProcedureService procedureService;
	TimeslotService timeslotService;
	ProcedureMapper procedureMapper;

	@Override
	public ProcedureDto create(ProcedureDto procedureDto) {
		Procedure procedure = procedureMapper.toEntity(SecurityUtils.getAuthUserId(), procedureDto);
		Procedure savedProcedure = procedureService.save(procedure);
		return procedureMapper.toDto(savedProcedure);
	}

	@Override
	public ProcedureDto patch(UUID procedureId, ProcedureDto procedureDto) {
		Procedure procedure = procedureService.findByIdAndMasterId(procedureId, SecurityUtils.getAuthUserId());

		procedureMapper.patchMerge(procedureDto, procedure);
		Procedure updatedProcedure = procedureService.save(procedure);

		return procedureMapper.toDto(updatedProcedure);
	}

	@Override
	public Page<ProcedureDto> getByMaster(UUID masterId, Pageable pageable) {
		return procedureService
				.findAllByMasterIdAndStatus(masterId, ProcedureStatus.ACTIVE, pageable)
				.map(procedureMapper::toDto);
	}

	@Override
	public List<TimeslotResponseDto> getAvailableTimeslots(UUID procedureId, AvailableDatesSearchDto datesSearchDto) {
		Procedure procedure = procedureService.findById(procedureId);
		return timeslotService.getAvailableTimeslots(datesSearchDto, procedure)
				.entrySet()
				.stream()
				.map(dateToTimeslots -> new TimeslotResponseDto(dateToTimeslots.getKey(), dateToTimeslots.getValue()))
				.toList();
	}

	@Override
	@Transactional
	public void delete(UUID procedureId) {
		Procedure procedure = procedureService.findByIdAndMasterId(procedureId, SecurityUtils.getAuthUserId());
		procedure.setStatus(ProcedureStatus.DELETED);
		procedureService.save(procedure);
	}

}
