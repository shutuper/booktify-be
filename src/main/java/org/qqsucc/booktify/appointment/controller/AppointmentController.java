package org.qqsucc.booktify.appointment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.appointment.controller.dto.*;
import org.qqsucc.booktify.appointment.controller.facade.AppointmentFacade;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Tag(name = "appointment")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class AppointmentController {

	AppointmentFacade appointmentFacade;

	@GetMapping("/private/appointments")
	public ResponseEntity<Page<AppointmentRespDto>> getAppointments(@SortDefault(value = "startDate", direction = DESC)
																	@RequestParam(defaultValue = "false") Boolean showCanceled,
																	@ParameterObject Pageable pageable) {
		Page<AppointmentRespDto> response = appointmentFacade.getAppointments(showCanceled, pageable);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Permissions: [ROLE_CLIENT]")
	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@PostMapping("/private/appointments")
	public ResponseEntity<AppointmentDto> bookAppointment(@RequestBody @Valid AppointmentReqDto appointmentReqDto) {
		AppointmentDto response = appointmentFacade.bookAppointment(appointmentReqDto);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Permissions: [ROLE_MASTER]")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	@PostMapping("/private/masters/appointments")
	public ResponseEntity<AppointmentDto> bookAppointmentByMaster(@RequestBody @Valid AppointmentMasterReqDto appointmentReqDto) {
		AppointmentDto response = appointmentFacade.bookAppointmentByMaster(appointmentReqDto);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/private/appointments/{appointmentId}")
	public ResponseEntity<AppointmentDto> cancelAppointment(@PathVariable UUID appointmentId,
															@RequestBody @Valid AppointmentCancelDto cancelDto) {
		AppointmentDto response = appointmentFacade.cancelAppointment(appointmentId, cancelDto);
		return ResponseEntity.ok(response);
	}

}
