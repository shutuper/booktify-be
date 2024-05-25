package org.qqsucc.booktify.procedure.controller.facade;

import org.qqsucc.booktify.procedure.controller.dto.AvailableDatesSearchDto;
import org.qqsucc.booktify.procedure.controller.dto.ProcedureDto;
import org.qqsucc.booktify.procedure.controller.dto.TimeslotResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProcedureFacade {

	ProcedureDto create(ProcedureDto procedureDto);

	ProcedureDto patch(UUID procedureId, ProcedureDto procedureDto);

	Page<ProcedureDto> getByMaster(UUID masterId, Pageable pageable);

	List<TimeslotResponseDto> getAvailableTimeslots(UUID procedureId, AvailableDatesSearchDto datesSearchDto);

	void delete(UUID procedureId);

}
