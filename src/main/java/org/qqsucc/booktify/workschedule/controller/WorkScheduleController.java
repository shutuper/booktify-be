package org.qqsucc.booktify.workschedule.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.workschedule.controller.dto.MultiWorkScheduleDto;
import org.qqsucc.booktify.workschedule.controller.facade.WorkScheduleFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@Tag(name = "work-schedule")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class WorkScheduleController {

	WorkScheduleFacade workScheduleFacade;

	@Operation(summary = "Permissions: [ROLE_MASTER]")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	@GetMapping("/private/masters/work-schedule")
	public ResponseEntity<MultiWorkScheduleDto> getMasterWorkSchedule() {
		return ResponseEntity.ok(workScheduleFacade.getMasterWorkSchedule());
	}

	@Operation(summary = "Permissions: [ROLE_MASTER]")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	@PutMapping("/private/masters/work-schedule")
	public ResponseEntity<MultiWorkScheduleDto> putMasterWorkSchedule(@RequestBody @Valid MultiWorkScheduleDto workSchedulesDto) {
		return ResponseEntity.ok(workScheduleFacade.putMasterWorkSchedule(workSchedulesDto));
	}

}
