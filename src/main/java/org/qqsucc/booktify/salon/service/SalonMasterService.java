package org.qqsucc.booktify.salon.service;

import org.qqsucc.booktify.salon.repository.entity.SalonMaster;
import org.qqsucc.booktify.salon.repository.entity.SalonMasterInvite;

import java.util.UUID;

public interface SalonMasterService {

	void save(SalonMaster salonMaster);

	void saveInvite(SalonMasterInvite salonMasterInvite);

	boolean existsByMasterId(UUID masterId);

	SalonMasterInvite findByInviteToken(String inviteToken);

	void deleteMasterInviteById(UUID masterInviteId);
}
