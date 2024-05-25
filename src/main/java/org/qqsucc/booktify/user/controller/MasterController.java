package org.qqsucc.booktify.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.user.controller.dto.MasterDto;
import org.qqsucc.booktify.user.controller.dto.MasterPrivateDto;
import org.qqsucc.booktify.user.controller.facade.MasterFacade;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Tag(name = "master")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class MasterController {

	MasterFacade masterFacade;

	@GetMapping("/public/salons/{salonId}/masters/{masterId}")
	public ResponseEntity<MasterDto> getSalonMaster(@PathVariable UUID salonId,@PathVariable UUID masterId) {
		MasterDto response = masterFacade.getSalonMaster(salonId, masterId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/public/salons/{salonId}/masters")
	public ResponseEntity<Page<MasterDto>> getSalonMasters(@PathVariable UUID salonId,
														   @SortDefault(sort = "createdDate", direction = DESC)
														   @ParameterObject Pageable pageable) {
		Page<MasterDto> response = masterFacade.getSalonMasters(salonId, pageable);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Permissions: [ROLE_MASTER]")
	@PreAuthorize("hasRole('ROLE_MASTER')")
	@GetMapping("/private/salons/{salonId}/masters")
	public ResponseEntity<Page<MasterPrivateDto>> getSalonMastersPrivate(@PathVariable UUID salonId,
																		 @SortDefault(sort = "createdDate", direction = DESC)
																		 @ParameterObject Pageable pageable) {
		Page<MasterPrivateDto> response = masterFacade.getSalonMastersPrivate(salonId, pageable);
		return ResponseEntity.ok(response);
	}

}
