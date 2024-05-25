package org.qqsucc.booktify.salon.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.qqsucc.booktify.common.exception.BusinessException;
import org.qqsucc.booktify.common.exception.NotFoundException;
import org.qqsucc.booktify.salon.repository.SalonMasterInviteRepository;
import org.qqsucc.booktify.salon.repository.SalonMasterRepository;
import org.qqsucc.booktify.salon.repository.entity.SalonMaster;
import org.qqsucc.booktify.salon.repository.entity.SalonMasterInvite;
import org.qqsucc.booktify.salon.service.SalonMasterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SalonMasterServiceImpl implements SalonMasterService {

	SalonMasterInviteRepository salonMasterInviteRepository;
	SalonMasterRepository salonMasterRepository;

	@Override
	public void save(SalonMaster salonMaster) {
		salonMasterRepository.save(salonMaster);
	}

	@Override
	public void saveInvite(SalonMasterInvite salonMasterInvite) {
		salonMasterInviteRepository.save(salonMasterInvite);
	}

	@Override
	public boolean existsByMasterId(UUID masterId) {
		return salonMasterRepository.existsByMasterId(masterId);
	}

	@Override
	public SalonMasterInvite findByInviteToken(String inviteToken) {
		SalonMasterInvite masterInvite = salonMasterInviteRepository
				.findByToken(inviteToken)
				.orElseThrow(() -> new NotFoundException("Invite token not found"));

		if (Instant.now().isAfter(masterInvite.getExpirationDate())) {
			throw new BusinessException("Invite token is expired, pls request new invitaion");
		}

		return masterInvite;
	}

	@Override
	@Transactional
	public void deleteMasterInviteById(UUID masterInviteId) {
		salonMasterInviteRepository.deleteById(masterInviteId);
	}
}
