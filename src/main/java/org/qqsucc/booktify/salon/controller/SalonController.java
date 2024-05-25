package org.qqsucc.booktify.salon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.salon.controller.dto.SalonDto;
import org.qqsucc.booktify.salon.controller.dto.SalonMasterInviteDto;
import org.qqsucc.booktify.salon.controller.facade.SalonFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Tag(name = "salon")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SalonController {

	SalonFacade salonFacade;

	@Operation(summary = "Permissions: [ROLE_MASTER]")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	@PostMapping("/private/salons")
	public ResponseEntity<SalonDto> create(@RequestBody @Valid SalonDto salonDto) {
		SalonDto response = salonFacade.create(salonDto);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Permissions: [ROLE_MASTER]")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	@PostMapping("/private/salons/masters/invites")
	public ResponseEntity<Void> inviteMaster(@RequestBody @Valid SalonMasterInviteDto inviteDto) {
		salonFacade.inviteMaster(inviteDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/public/salons/masters/invites")
	public ResponseEntity<SalonDto> findSalonByInviteToken(@RequestParam String inviteToken) {
		SalonDto response = salonFacade.findSalonByInviteToken(inviteToken);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Permissions: [ROLE_MASTER]")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	@PutMapping("/private/salons/{salonId}")
	public ResponseEntity<SalonDto> update(@PathVariable UUID salonId, @RequestBody @Valid SalonDto salonDto) {
		SalonDto response = salonFacade.update(salonId, salonDto);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Permissions: [ROLE_MASTER]", description = "Find salon by master ID")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	@GetMapping("/private/masters/{masterId}/salons")
	public ResponseEntity<SalonDto> findByMasterId(@PathVariable UUID masterId) {
		SalonDto response = salonFacade.findByMasterId(masterId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/public/salons/{salonLinkName}")
	public ResponseEntity<SalonDto> findByLinkName(@PathVariable String salonLinkName) {
		SalonDto response = salonFacade.findByLinkName(salonLinkName);
		return ResponseEntity.ok(response);
	}


}
