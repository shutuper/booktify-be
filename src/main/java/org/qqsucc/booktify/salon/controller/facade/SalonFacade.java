package org.qqsucc.booktify.salon.controller.facade;

import org.qqsucc.booktify.salon.controller.dto.SalonDto;
import org.qqsucc.booktify.salon.controller.dto.SalonMasterInviteDto;

import java.util.UUID;

public interface SalonFacade {

	SalonDto create(SalonDto salonDto);

	SalonDto update(UUID salonId, SalonDto salonDto);

	SalonDto findByMasterId(UUID masterId);

	SalonDto findByLinkName(String salonLinkName);

	void inviteMaster(SalonMasterInviteDto inviteDto);

	SalonDto findSalonByInviteToken(String inviteToken);
}
