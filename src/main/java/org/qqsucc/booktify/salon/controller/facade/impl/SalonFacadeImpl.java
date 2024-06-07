package org.qqsucc.booktify.salon.controller.facade.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.qqsucc.booktify.common.exception.AlreadyExistException;
import org.qqsucc.booktify.common.util.SecurityUtils;
import org.qqsucc.booktify.notification.service.NotificationService;
import org.qqsucc.booktify.salon.controller.dto.SalonDto;
import org.qqsucc.booktify.salon.controller.dto.SalonMasterInviteDto;
import org.qqsucc.booktify.salon.controller.facade.SalonFacade;
import org.qqsucc.booktify.salon.controller.mapper.SalonMapper;
import org.qqsucc.booktify.salon.repository.entity.Salon;
import org.qqsucc.booktify.salon.repository.entity.SalonMaster;
import org.qqsucc.booktify.salon.repository.entity.SalonMasterInvite;
import org.qqsucc.booktify.salon.service.SalonMasterService;
import org.qqsucc.booktify.salon.service.SalonService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class SalonFacadeImpl implements SalonFacade {

	NotificationService notificationService;
	SalonMasterService salonMasterService;
	SalonService salonService;
	SalonMapper salonMapper;

	@Override
	@Transactional
	public SalonDto create(SalonDto salonDto) {
		throwIfLinkNameAlreadyUsed(salonDto.getLinkName());

		UUID authUserId = SecurityUtils.getAuthUserId();
		boolean masterAlreadyHasSalon = salonMasterService.existsByMasterId(authUserId);

		if (masterAlreadyHasSalon) {
			throw new AlreadyExistException("Master already has salon");
		}

		Salon salon = salonService.save(salonMapper.toEntity(salonDto));

		// saving master association with a new salon
		salonMasterService.save(
				SalonMaster.builder()
						.masterId(authUserId)
						.salonId(salon.getId())
						.build()
		);

		return salonMapper.toDto(salon);
	}

	@Override
	public SalonDto update(UUID salonId, SalonDto salonDto) {
		Salon salon = salonService.findByMasterIdAndId(SecurityUtils.getAuthUserId(), salonId);

		boolean linkNameChanged = ObjectUtils.notEqual(salonDto.getLinkName(), salon.getLinkName());

		if (linkNameChanged) {
			throwIfLinkNameAlreadyUsed(salonDto.getLinkName());
		}

		salonMapper.putMerge(salonDto, salon);

		return salonMapper.toDto(salonService.save(salon));
	}

	@Override
	public SalonDto findByMasterId(UUID masterId) {
		return salonMapper.toDto(salonService.findByMasterId(masterId));
	}

	@Override
	public SalonDto findByLinkName(String salonLinkName) {
		return salonMapper.toDto(salonService.findByLinkName(salonLinkName));
	}

	@Override
	@Transactional
	public void inviteMaster(SalonMasterInviteDto inviteDto) {
		Salon salon = salonService.findByMasterId(SecurityUtils.getAuthUserId());
		String token = UUID.randomUUID() + "-" + UUID.randomUUID();

		salonMasterService.saveInvite(
				SalonMasterInvite.builder()
						.token(token)
						.expirationDate(Instant.now().plus(1, ChronoUnit.DAYS))
						.salonId(salon.getId())
						.build()
		);

		notificationService.sendMasterInvitation(inviteDto.getEmail(), token, salon.getTitle());
	}

	@Override
	public SalonDto findSalonByInviteToken(String inviteToken) {
		Salon salon = salonMasterService.findByInviteToken(inviteToken).getSalon();
		return salonMapper.toDto(salon);
	}

	private void throwIfLinkNameAlreadyUsed(String linkName) {
		boolean existsByLinkName = salonService.existsByLinkName(linkName);

		if (existsByLinkName) {
			throw new AlreadyExistException("Salon with such a link name already exists");
		}
	}
}
