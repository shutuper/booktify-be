package org.qqsucc.booktify.procedure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.procedure.controller.dto.AvailableDatesSearchDto;
import org.qqsucc.booktify.procedure.controller.dto.ProcedureDto;
import org.qqsucc.booktify.procedure.controller.dto.TimeslotResponseDto;
import org.qqsucc.booktify.procedure.controller.facade.ProcedureFacade;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Tag(name = "procedure")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class ProcedureController {

	ProcedureFacade procedureFacade;

	@Operation(summary = "Permissions: [ROLE_MASTER]")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	@PostMapping("/private/procedures")
	public ResponseEntity<ProcedureDto> create(@RequestBody @Valid ProcedureDto procedureDto) {
		ProcedureDto response = procedureFacade.create(procedureDto);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Permissions: [ROLE_MASTER]")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	@PatchMapping("/private/procedures/{procedureId}")
	public ResponseEntity<ProcedureDto> patch(@PathVariable UUID procedureId, @RequestBody @Valid ProcedureDto procedureDto) {
		ProcedureDto response = procedureFacade.patch(procedureId, procedureDto);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Permissions: [ROLE_MASTER]")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	@DeleteMapping("/private/procedures/{procedureId}")
	public ResponseEntity<Void> delete(@PathVariable UUID procedureId) {
		procedureFacade.delete(procedureId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/public/masters/{masterId}/procedures")
	public ResponseEntity<Page<ProcedureDto>> getByMaster(@PathVariable UUID masterId,
														  @SortDefault(sort = "createdDate", direction = DESC)
														  @ParameterObject Pageable pageable) {
		Page<ProcedureDto> response = procedureFacade.getByMaster(masterId, pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/public/procedures/{procedureId}/available-timeslots")
	public ResponseEntity<List<TimeslotResponseDto>> getAvailableTimeslots(@PathVariable UUID procedureId,
																		   @ParameterObject @Valid
																		   AvailableDatesSearchDto datesSearchDto) {
		List<TimeslotResponseDto> response = procedureFacade.getAvailableTimeslots(procedureId, datesSearchDto);
		return ResponseEntity.ok(response);
	}

}
